package com.example.bfinerocks.flashcard.models;

import java.util.List;

/**
 * Created by BFineRocks on 12/28/14.
 */
public class Deck {
    private List<WordCard> myDeck;
    private String deckName;

    public Deck(String deckName){
        this.deckName = deckName;
    }

    public void addWordCardToDeck(WordCard wordCard){
        myDeck.add(wordCard);
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
}
