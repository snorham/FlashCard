package com.example.bfinerocks.flashcard.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.activities.WordCardGeneratorActivity;
import com.example.bfinerocks.flashcard.adapters.DeckListCustomAdapter;
import com.example.bfinerocks.flashcard.constants.ConstantsForReference;
import com.example.bfinerocks.flashcard.firebase.FirebaseStorage;
import com.example.bfinerocks.flashcard.interfaces.FragmentTransitionInterface;
import com.example.bfinerocks.flashcard.models.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class HomepageFragment extends Fragment implements OnClickListener, OnItemClickListener{

   // private static final String USER_FIREBASE_REFERENCE = "firebase_user_ref";
    private TextView linkToCreateNewDeck;
    private Handler uiHandler;
    private List<Deck> listOfDecks;
    private DeckListCustomAdapter deckAdapter;
    private ListView listOfDecksSaved;


    public static HomepageFragment newInstance(String user){
        Bundle bundle = new Bundle();
        bundle.putString(ConstantsForReference.USER_FIREBASE_REFERENCE, user);
        HomepageFragment homepageFragment = new HomepageFragment();
        homepageFragment.setArguments(bundle);
        return homepageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_homepage, container, false);
        TextView welcomeUser = (TextView) rootView.findViewById(R.id.homepage_welcome);
        listOfDecksSaved = (ListView) rootView.findViewById(R.id.homepage_listView);
        listOfDecksSaved.addHeaderView(inflater.inflate(R.layout.deck_header_item, null));
        linkToCreateNewDeck = (TextView) rootView.findViewById(R.id.txt_create_new_deck);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linkToCreateNewDeck.setOnClickListener(this);
        listOfDecksSaved.setOnClickListener(this);
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
        String userReference = getArguments().getString(ConstantsForReference.USER_FIREBASE_REFERENCE);
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
        CreateAndReviewFragment createAndReviewFragment = CreateAndReviewFragment.newInstance();
        fti.onFragmentChange(createAndReviewFragment);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Deck deckSelected = (Deck) adapterView.getSelectedItem();
        Intent cardGeneratorIntent = new Intent(getActivity(), WordCardGeneratorActivity.class);
        cardGeneratorIntent.putExtra("deckSelected", deckSelected);
    }
}
