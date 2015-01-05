package com.example.bfinerocks.flashcard.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.adapters.WordCardCreatorCustomAdapter;
import com.example.bfinerocks.flashcard.constants.ConstantsForReference;
import com.example.bfinerocks.flashcard.firebase.FirebaseStorage;
import com.example.bfinerocks.flashcard.fragments.WordCardEditDialog.WordCardEditDialogInterface;
import com.example.bfinerocks.flashcard.fragments.WordEntryDialogFragment.WordCardCreatorDialogInterface;
import com.example.bfinerocks.flashcard.models.Deck;
import com.example.bfinerocks.flashcard.models.WordCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/22/14.
 */
public class CreateNewDeckFragment extends Fragment implements WordCardCreatorDialogInterface, OnClickListener, OnItemClickListener {

    private static final String DIALOG_FRAG_TAG = "WordEntryDialog";
    public List<WordCard> listOfWordCards;
    private ListView listView;
    private WordCardCreatorCustomAdapter adapter;
    private Button saveListButton;
    private EditText deckNameEditText;

    public static CreateNewDeckFragment newInstance(){

        return new CreateNewDeckFragment();
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
        saveListButton.setOnClickListener(this);
        showWordEntryDialogFragment();
    }

    public void showWordEntryDialogFragment(){
        DialogFragment wordEntryFragment = WordEntryDialogFragment.newInstance(this);
        wordEntryFragment.show(getActivity().getFragmentManager(), DIALOG_FRAG_TAG);
    }

    public void updateAdapterWithNewCards(WordCard wordCard){
        listOfWordCards.add(wordCard);
        adapter.notifyDataSetChanged();
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

    @Override
    public void positiveClickNextWordCard() {
        showWordEntryDialogFragment();
    }

    @Override
    public void negativeClickNoMoreCards() {
        Toast.makeText(getActivity(), R.string.toast_make_changes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void wordCardCreated(WordCard wordCard) {
        updateAdapterWithNewCards(wordCard);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final int itemSelected = i;
        WordCard wordCard = (WordCard) adapterView.getItemAtPosition(itemSelected);
        WordCardEditDialog wordCardEditDialog = WordCardEditDialog.newInstance(wordCard, new WordCardEditDialogInterface() {
            @Override
            public void wordCardEditedByUser(WordCard wordCard) {
                listOfWordCards.set(itemSelected, wordCard);
                adapter.notifyDataSetChanged();
            }
        });
        wordCardEditDialog.show(getActivity().getFragmentManager(), "Editor");
    }

}
