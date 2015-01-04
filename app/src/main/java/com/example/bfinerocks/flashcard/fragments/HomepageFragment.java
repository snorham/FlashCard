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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class HomepageFragment extends Fragment implements OnClickListener, OnItemClickListener, OnItemLongClickListener{

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
        listOfDecksSaved.setOnItemClickListener(this);
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
/*        bundle.putParcelableArrayList("com.example.bfinerocks.flashcard.models.Deck",(ArrayList) deckSelected.getMyDeck());
        bundle.putString("title", deckSelected.getDeckName());*/
       cardGeneratorIntent.putExtra("com.example.bfinerocks.flashcard.models.Deck", deckSelected);
       cardGeneratorIntent.putExtras(bundle);
        startActivity(cardGeneratorIntent);
/*        FlashCardGeneratorFragment fg = new FlashCardGeneratorFragment();
        fg.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fg)
                .commit();*/


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Deck deckSelected = (Deck) adapterView.getItemAtPosition(i);
        return false;
    }
}
