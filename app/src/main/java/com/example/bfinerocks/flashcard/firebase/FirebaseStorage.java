package com.example.bfinerocks.flashcard.firebase;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class FirebaseStorage {

    private Firebase referenceWithUser = null;
    private final static String DECK_LEVL_REF_KEY = "decks";

    public void createFirebaseReferenceWithUserNameForReference(String userName){
        Firebase refPriorToUser = new Firebase("https://dazzling-heat-5107.firebaseio.com/flash-cards/users");
        referenceWithUser = refPriorToUser.child(userName);
    }

    public Firebase getReferenceWithUser(){
        return referenceWithUser;
    }

    public void addNewDeckToFirebaseUserReference(Map<String,Deck> deckOfCards){
        Firebase deckLevelRef = getReferenceWithUser().child(DECK_LEVL_REF_KEY);
        deckLevelRef.push().setValue(deckOfCards);
    }
}
