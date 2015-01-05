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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.adapters.WordCardCreatorCustomAdapter;
import com.example.bfinerocks.flashcard.constants.ConstantsForReference;
import com.example.bfinerocks.flashcard.firebase.FirebaseStorage;
import com.example.bfinerocks.flashcard.models.Deck;
import com.example.bfinerocks.flashcard.models.WordCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/22/14.
 */
public class ReviewDeckFragment extends Fragment implements OnClickListener {

    private static final String DIALOG_FRAG_TAG = "WordEntryDialog";
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
        updateListViewWithSelectedDeck();
        saveListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String deckName = deckNameEditText.getText().toString().trim();
        if(deckName.isEmpty()){
            Toast.makeText(getActivity(), R.string.toast_need_deck_name, Toast.LENGTH_SHORT).show();
        }
        else{
            Deck deckOfCards = new Deck(deckName);
            deckOfCards.addListOfWordCardsToDeck(listOfWordCards);
            sendDeckToFirebase(deckOfCards);
        }
    }


    public void sendDeckToFirebase(Deck deckOfCards){
        FirebaseStorage firebaseStorage = new FirebaseStorage();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userName = sharedPreferences.getString(ConstantsForReference.USER_NAME_PREFERENCE, "user");
        firebaseStorage.addNewDeckToFirebaseUserReference(userName, deckOfCards);
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
}
