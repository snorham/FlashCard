package com.example.bfinerocks.flashcard.firebase;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

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

    //the object in this map is a Deck object not yet create //todo create deck object
    public void addNewDeckToFirebaseUserReference(Map<String,Object> deckOfCards){
        getReferenceToUsersDeckLevel().push().setValue(deckOfCards);
    }

    public void getUsersDecksFromFirebase(){
        getReferenceToUsersDeckLevel().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot != null){
                    Map<String, Object> deckOfCards = (Map<String,Object>) dataSnapshot.getValue();
                    StudyShelf myStudyShelf = new StudyShelf();
                    myStudyShelf.add(deckOfCards);
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
