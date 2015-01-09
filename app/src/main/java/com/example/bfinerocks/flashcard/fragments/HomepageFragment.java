package com.example.bfinerocks.flashcard.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.activities.FlashCardGeneratorActivity;
import com.example.bfinerocks.flashcard.adapters.DeckListCustomAdapter;
import com.example.bfinerocks.flashcard.constants.ConstantsForReference;
import com.example.bfinerocks.flashcard.firebase.FirebaseStorage;
import com.example.bfinerocks.flashcard.interfaces.FragmentTransitionInterface;
import com.example.bfinerocks.flashcard.models.Deck;
import com.example.bfinerocks.flashcard.tools.FlashCardTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class HomepageFragment extends Fragment implements OnClickListener, OnItemClickListener, OnItemLongClickListener, DialogInterface.OnClickListener{

    private TextView linkToCreateNewDeck;
    private Handler uiHandler;
    private List<Deck> listOfDecks;
    private DeckListCustomAdapter deckAdapter;
    private ListView listOfDecksSaved;
    private String userReference;
    private Deck deckSelected;


    public static HomepageFragment newInstance(String user){
        Bundle bundle = new Bundle();
        bundle.putString(ConstantsForReference.USER_FIREBASE_REFERENCE, user);
        HomepageFragment homepageFragment = new HomepageFragment();
        homepageFragment.setArguments(bundle);
        return homepageFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        FlashCardTools.enableNavigationalHomeButton(getActivity(),false);
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new FlashCardSettingsFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_homepage, container, false);
        setHasOptionsMenu(true);
        TextView welcomeUser = (TextView) rootView.findViewById(R.id.homepage_welcome);
        listOfDecksSaved = (ListView) rootView.findViewById(R.id.homepage_listView);
        View headerView = inflater.inflate(R.layout.deck_header_item, null, false);
        listOfDecksSaved.addHeaderView(headerView, null, false);
        linkToCreateNewDeck = (TextView) rootView.findViewById(R.id.txt_create_new_deck);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linkToCreateNewDeck.setOnClickListener(this);
        listOfDecksSaved.setOnItemClickListener(this);
        listOfDecksSaved.setOnItemLongClickListener(this);
        listOfDecks = new ArrayList<Deck>();
        deckAdapter = new DeckListCustomAdapter(getActivity(), R.layout.deck_item, listOfDecks);
        listOfDecksSaved.setAdapter(deckAdapter);
        uiHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.i("messRec", msg.obj.toString());
                Deck deck = (Deck) msg.obj;
                listOfDecks.add(deck);
                deckAdapter.notifyDataSetChanged();
            }
        };
        getUpdatedSavedDeckFromFirebase();

    }

    public void getUpdatedSavedDeckFromFirebase(){
            userReference = getArguments().getString(ConstantsForReference.USER_FIREBASE_REFERENCE);
            if(userReference != null){
            FirebaseStorage firebaseStorage = new FirebaseStorage(uiHandler);
            firebaseStorage.createFirebaseReferenceWithUserNameForReference(userReference);
            firebaseStorage.appendFirebaseReferenceWithDeckLevelReference();
            firebaseStorage.getUsersDecksFromFirebase();
        }
    }

    @Override
    public void onClick(View view) {
        FragmentTransitionInterface fti = (FragmentTransitionInterface) getActivity();
        CreateNewDeckFragment createNewDeckFragment = CreateNewDeckFragment.newInstance();
        fti.onFragmentChange(createNewDeckFragment);
    }

    public void transitionToCreateAndReviewFragment(){
        try {
            FragmentTransitionInterface fti = (FragmentTransitionInterface) getActivity();
            CreateNewDeckFragment createNewDeckFragment = CreateNewDeckFragment.newInstance();
            fti.onFragmentChange(createNewDeckFragment);
        } catch (ClassCastException e){
            throw new IllegalStateException("HomepageFragment must be created with an activity that implements FragmentTransitionInterface", e);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Deck deckSelected = (Deck) adapterView.getItemAtPosition(i);
        Intent cardGeneratorIntent = new Intent(getActivity(), FlashCardGeneratorActivity.class);
        Bundle bundle = new Bundle();
        cardGeneratorIntent.putExtra(ConstantsForReference.DECK_TO_FLASH, deckSelected);
        cardGeneratorIntent.putExtras(bundle);
        startActivity(cardGeneratorIntent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        deckSelected = (Deck) adapterView.getItemAtPosition(i);
        displayEditOrDeleteDialog();
        return true;
    }

    public void displayEditOrDeleteDialog(){
        AlertDialog.Builder editDialog = new AlertDialog.Builder(getActivity());
        editDialog.setTitle(R.string.dialog_edit_definition_header);
        editDialog.setMessage(R.string.dialog_delete_directions);
        editDialog.setPositiveButton(R.string.dialog_delete_edit_btn, this);
        editDialog.setNeutralButton(R.string.dialog_delete_delete_btn, this);
        editDialog.setNegativeButton(R.string.dialog_delete_cancel_btn, this);
        editDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                FragmentTransitionInterface fti = (FragmentTransitionInterface) getActivity();
                ReviewDeckFragment reviewDeckFragment = ReviewDeckFragment.newInstance(deckSelected);
                fti.onFragmentChange(reviewDeckFragment);
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                FirebaseStorage firebaseStorage = new FirebaseStorage();
                firebaseStorage.deleteDeckFromFirebase(userReference, deckSelected);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }
}
