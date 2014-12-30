package com.example.bfinerocks.flashcard.models;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/28/14.
 */
public class Deck implements android.os.Parcelable {
    private List<WordCard> myDeck;
    private String deckName;

    public Deck(String deckName){
        this.deckName = deckName;
        myDeck = new ArrayList<WordCard>();
    }

    public String getDeckName(){
        return deckName;
    }

    public void addWordCardToDeck(WordCard wordCard){
        myDeck.add(wordCard);
    }

    public void addListOfWordCardsToDeck(List<WordCard> listOfCards){
        myDeck.addAll(listOfCards);
    }

    public WordCard getWordCardFromDeck(int cardNumber){
        return myDeck.get(cardNumber);
    }

    public void editAWordCardInDeck(int cardEditedNumber, WordCard editedWordCard){
        myDeck.set(cardEditedNumber, editedWordCard);
    }

    public List<WordCard> getMyDeck(){
        return myDeck;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(myDeck);
        dest.writeString(this.deckName);
    }

    private Deck(Parcel in) {
        in.readTypedList(myDeck, WordCard.CREATOR);
        this.deckName = in.readString();
    }

    public static final Creator<Deck> CREATOR = new Creator<Deck>() {
        public Deck createFromParcel(Parcel source) {
            return new Deck(source);
        }

        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };
}
