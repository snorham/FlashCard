package com.example.bfinerocks.flashcard.firebase;

import android.util.Log;

import com.example.bfinerocks.flashcard.models.WordCard;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class FirebaseStorage {

    private Firebase referenceWithUser = null;
    private Firebase referenceToUsersDeckLevel = null;
    private final static String DECK_LEVL_REF_KEY = "study-shelf";


    public void createFirebaseReferenceWithUserNameForReference(String userName){
        Firebase refPriorToUser = new Firebase("https://dazzling-heat-5107.firebaseio.com/flash-cards/users");
        referenceWithUser = refPriorToUser.child(userName);
    }

    public Firebase getReferenceWithUser(){
        return referenceWithUser;
    }

    public void appendFirebaseReferenceWithDeckLevelReference(){
        referenceToUsersDeckLevel = getReferenceWithUser().child(DECK_LEVL_REF_KEY);
    }

    public Firebase getReferenceToUsersDeckLevel(){
        return referenceToUsersDeckLevel;
    }


    public void addNewDeckToFirebaseUserReference(String userName, String listName, List<WordCard> listOfCards){
        createFirebaseReferenceWithUserNameForReference(userName);
        appendFirebaseReferenceWithDeckLevelReference();
        Map<String, Object> deck = new HashMap<String, Object>();
        deck.put("cards", listOfCards);
        deck.put("deckname", listName);
        getReferenceToUsersDeckLevel().push().setValue(deck);

    }

    public void getUsersDecksFromFirebase(){
        getReferenceToUsersDeckLevel().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    Map<String, Object> mapTest = (Map<String, Object>) dataSnapshot.getValue();
                    Log.i("mapTest", mapTest.get("deckname").toString());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
