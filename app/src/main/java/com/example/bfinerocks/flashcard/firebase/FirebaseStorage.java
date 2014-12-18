package com.example.bfinerocks.flashcard.firebase;

import com.firebase.client.Firebase;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class FirebaseStorage {

    private Firebase referenceWithUser = null;

    public void createFirebaseReferenceWithUserNameForReference(String userName){
        Firebase refPriorToUser = new Firebase("https://dazzling-heat-5107.firebaseio.com/flash-cards/users");
        referenceWithUser = refPriorToUser.child(userName).child("decks");
    }

    public Firebase getReferenceWithUser(){
        return referenceWithUser;
    }
}
