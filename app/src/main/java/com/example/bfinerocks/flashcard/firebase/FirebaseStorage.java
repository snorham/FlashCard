package com.example.bfinerocks.flashcard.firebase;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.bfinerocks.flashcard.models.Deck;
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
    private Handler handler;

    public FirebaseStorage(){

    }
    public FirebaseStorage(Handler handler){
        this.handler = handler;
    }


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


    public void addNewDeckToFirebaseUserReference(String userName, Deck deck){
        createFirebaseReferenceWithUserNameForReference(userName);
        appendFirebaseReferenceWithDeckLevelReference();
        getReferenceToUsersDeckLevel().push().setValue(deck);

    }

    public void updateFirebaseWithUpdatedDeck(String userName, final Deck deckUpdated){
        Log.i("deckUpdatedSent", deckUpdated.getMyDeck().toString());
        createFirebaseReferenceWithUserNameForReference(userName);
        appendFirebaseReferenceWithDeckLevelReference();
        getReferenceToUsersDeckLevel().child(deckUpdated.getFirebaseUID()).setValue(deckUpdated);
    }

    public void getUsersDecksFromFirebase(){
        getReferenceToUsersDeckLevel().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                  sendMessageToUIHandler(getDeckFromFirebase(dataSnapshot));
                    Log.i("data", dataSnapshot.toString());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot != null){
                   // sendMessageToUIHandler(getDeckFromFirebase(dataSnapshot));
                    Log.i("data", dataSnapshot.toString());
                }
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

    public Deck getDeckFromFirebase(DataSnapshot dataSnapshot){
        Map<String, Object> deckMap = (Map<String, Object>)dataSnapshot.getValue();
        Log.i("deckMap", dataSnapshot.getKey());
        String deckName = deckMap.get("deckName").toString();
        Deck deck = new Deck(deckName);
        deck.setFirebaseUID(dataSnapshot.getKey());
        Log.i("uidSet", deck.getFirebaseUID());
        List<Object> list = (List<Object>) deckMap.get("myDeck");

        for(int i = 0; i < list.size(); i++){
            HashMap<String, String> map = (HashMap) list.get(i);
            String word = map.get("wordSide");
            String def = map.get("definitionSide");
            WordCard wordCard1 = new WordCard(word);
            wordCard1.setDefinitionSide(def);
            deck.addWordCardToDeck(wordCard1);
        }
        return deck;
    }

    public void sendMessageToUIHandler(Deck deck){
        Message message = new Message();
        message.obj = deck;
        handler.sendMessage(message);
    }

}
