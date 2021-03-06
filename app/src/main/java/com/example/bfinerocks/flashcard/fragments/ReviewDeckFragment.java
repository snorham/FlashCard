package com.example.bfinerocks.flashcard.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.bfinerocks.flashcard.tools.FlashCardTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/22/14.
 */
public class ReviewDeckFragment extends Fragment implements OnClickListener, OnItemClickListener, WordCardCreatorDialogInterface {

    public List<WordCard> listOfWordCards;
    private ListView listView;
    private WordCardCreatorCustomAdapter adapter;
    private Button saveListButton;
    private EditText deckNameEditText;
    private Deck deckToUpdate;
    private View headerView;

    public static ReviewDeckFragment newInstance(Deck deck){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantsForReference.SELECTED_DECK_TO_REVIEW, deck);
        ReviewDeckFragment reviewDeckFragment = new ReviewDeckFragment();
        reviewDeckFragment.setArguments(bundle);
        return reviewDeckFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        FlashCardTools.enableNavigationalHomeButton(getActivity(), true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_and_review, container, false);
        setHasOptionsMenu(true);
        deckNameEditText = (EditText) rootView.findViewById(R.id.edtxt_list_title);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        saveListButton = (Button) rootView.findViewById(R.id.save_button);
        saveListButton.setText(getString(R.string.btn_save_changes));
        headerView = inflater.inflate(R.layout.item_word_card_list_header, null, true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listOfWordCards = new ArrayList<WordCard>();
        adapter = new WordCardCreatorCustomAdapter(getActivity(), R.layout.word_definition_item,listOfWordCards);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        headerView.setOnClickListener(this);
        listView.addHeaderView(headerView);
        updateListViewWithSelectedDeck();
        saveListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_button:
                deckToUpdate.setDeckName(deckNameEditText.getText().toString());
                sendDeckToFirebase(deckToUpdate);
                break;
            case R.id.word_card_list_header:
                showWordEntryDialogFragment();
                break;
        }
    }

    public void sendDeckToFirebase(Deck deckOfCards){
        FirebaseStorage firebaseStorage = new FirebaseStorage();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userName = sharedPreferences.getString(ConstantsForReference.USER_NAME_PREFERENCE, "user");
        firebaseStorage.updateFirebaseWithUpdatedDeck(userName, deckOfCards);
        saveListButton.setClickable(false);
        Toast.makeText(getActivity(),getString(R.string.changes_saved),Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStackImmediate();
    }

    public void updateListViewWithSelectedDeck(){
        deckToUpdate = getArguments().getParcelable(ConstantsForReference.SELECTED_DECK_TO_REVIEW);
        if(deckToUpdate != null){
            listOfWordCards = deckToUpdate.getMyDeck();
            deckNameEditText.setText(deckToUpdate.getDeckName());
            Log.i("wordCard", deckToUpdate.getWordCardFromDeck(0).getWordSide());
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

    public void showWordEntryDialogFragment(){
        DialogFragment wordEntryFragment = WordEntryDialogFragment.newInstance(this);
        wordEntryFragment.show(getActivity().getFragmentManager(), ConstantsForReference.DIALOG_FRAG_TAG);
    }

    @Override
    public void negativeClickNoMoreCards() {
        //
    }

    @Override
    public void wordCardCreated(WordCard wordCard) {
        listOfWordCards.add(wordCard);
        adapter.notifyDataSetChanged();
    }
}