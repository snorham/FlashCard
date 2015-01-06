package com.example.bfinerocks.flashcard.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.adapters.WordCardCreatorCustomAdapter;
import com.example.bfinerocks.flashcard.constants.ConstantsForReference;
import com.example.bfinerocks.flashcard.firebase.FirebaseStorage;
import com.example.bfinerocks.flashcard.fragments.WordCardEditDialog.WordCardEditDialogInterface;
import com.example.bfinerocks.flashcard.models.Deck;
import com.example.bfinerocks.flashcard.models.WordCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/22/14.
 */
public class ReviewDeckFragment extends Fragment implements OnClickListener, OnItemClickListener {

    public List<WordCard> listOfWordCards;
    private ListView listView;
    private WordCardCreatorCustomAdapter adapter;
    private Button saveListButton;
    private EditText deckNameEditText;

    public static ReviewDeckFragment newInstance(Deck deck){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantsForReference.SELECTED_DECK_TO_REVIEW, deck);
        ReviewDeckFragment reviewDeckFragment = new ReviewDeckFragment();
        reviewDeckFragment.setArguments(bundle);
        return reviewDeckFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_and_review, container, false);
        deckNameEditText = (EditText) rootView.findViewById(R.id.edtxt_list_title);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        saveListButton = (Button) rootView.findViewById(R.id.save_button);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listOfWordCards = new ArrayList<WordCard>();
        adapter = new WordCardCreatorCustomAdapter(getActivity(), R.layout.word_definition_item,listOfWordCards);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        updateListViewWithSelectedDeck();
        saveListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Deck deck = new Deck(deckNameEditText.getText().toString());
        sendDeckToFirebase(deck);
    }


    public void sendDeckToFirebase(Deck deckOfCards){
        FirebaseStorage firebaseStorage = new FirebaseStorage();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userName = sharedPreferences.getString(ConstantsForReference.USER_NAME_PREFERENCE, "user");
        firebaseStorage.updateFirebaseWithUpdatedDeck(userName, deckOfCards);
    }

    public void updateListViewWithSelectedDeck(){
        Deck deck = getArguments().getParcelable(ConstantsForReference.SELECTED_DECK_TO_REVIEW);
        if(deck != null){
            listOfWordCards = deck.getMyDeck();
            deckNameEditText.setText(deck.getDeckName());
            Log.i("wordCard", deck.getWordCardFromDeck(0).getWordSide());
            adapter.addAll(listOfWordCards);
            adapter.notifyDataSetChanged();
        }
        else{
            throw new IllegalStateException("Must supply a selected Deck to ReviewDeckFragment");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        final int itemSelected = position;
        WordCard wordCard = (WordCard) adapterView.getItemAtPosition(itemSelected);
        WordCardEditDialog wordCardEditDialog = WordCardEditDialog.newInstance(wordCard, new WordCardEditDialogInterface() {
            @Override
            public void wordCardEditedByUser(WordCard wordCard) {
                listOfWordCards.set(itemSelected, wordCard);
                adapter.notifyDataSetChanged();
                Log.i("ListUpdated", listOfWordCards.get(itemSelected).getWordSide());
            }
        });
        wordCardEditDialog.show(getActivity().getFragmentManager(), ConstantsForReference.EDIT_DIALOG_FRAG_TAG);
    }
}
