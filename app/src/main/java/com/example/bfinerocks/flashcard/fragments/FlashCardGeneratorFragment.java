package com.example.bfinerocks.flashcard.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.constants.ConstantsForPreferenceFile;
import com.example.bfinerocks.flashcard.models.Deck;
import com.example.bfinerocks.flashcard.models.WordCard;

import java.util.Random;

/**
 * Created by BFineRocks on 12/30/14.
 */
public class FlashCardGeneratorFragment extends Fragment implements OnClickListener {

    private TextView cardText;
    private Button backButton;
    private Button nextButton;
    private Deck deck;
    private int currentCard;
    private int numOfCardsInDeck;
    private boolean displayWordSide;
    private boolean setCardDisplayRandom;
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


    public static FlashCardGeneratorFragment newInstance(Deck currentDeck){
        Bundle bundle = new Bundle();
        bundle.putParcelable("deckSent", currentDeck);
        FlashCardGeneratorFragment flashCardGeneratorFragment = new FlashCardGeneratorFragment();
        flashCardGeneratorFragment.setArguments(bundle);
        return flashCardGeneratorFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_view, container, false);
        deck = getDeckFromBundle();
        numOfCardsInDeck = deck.getMyDeck().size()-1;
        cardText = (TextView) rootView.findViewById(R.id.card_text);
        setTextColorBasedOnUserPreference();
        setBackgroundColorBasedOnUserPreference();
        cardText.setOnClickListener(this);
        backButton = (Button) rootView.findViewById(R.id.btn_back);
        backButton.setOnClickListener(this);
        nextButton = (Button) rootView.findViewById(R.id.btn_next);
        nextButton.setOnClickListener(this);
        displayNextCardOnScreen();
        currentCard = 0;
        displayWordSide = true;
        setCardDisplayRandom = sharedPreferences.getBoolean(ConstantsForPreferenceFile.PREF_RANDOM_ORDER_KEY, false);
        currentCard = 0;
        cardText.setText(getWordCardFromDeck().getWordSide());
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_text:
                changeViewOnScreenClick();
                break;
            case R.id.btn_back:
                displayPriorCardOnScreen();
                break;
            case R.id.btn_next:
                displayNextCardOnScreen();
                break;
        }
    }

    public Deck getDeckFromBundle(){
        Bundle bundle = getArguments();
        return bundle.getParcelable("deckSent");
    }

    public WordCard getWordCardFromDeck(){
      return deck.getWordCardFromDeck(currentCard);
    }

    public void displayNextCardOnScreen(){
        if(setCardDisplayRandom){
            setRandomNumberForCardDisplayScreen();
            cardText.setText(getWordCardFromDeck().getWordSide());
        }
        else {
            if (currentCard < numOfCardsInDeck) {
                currentCard++;
                cardText.setText(getWordCardFromDeck().getWordSide());
            } else {
                currentCard = 0;
                cardText.setText(getWordCardFromDeck().getWordSide());
            }
        }
    }

    public void setRandomNumberForCardDisplayScreen(){
        Random randomCard = new Random();
        currentCard = randomCard.nextInt(deck.getMyDeck().size());
    }


    public void displayPriorCardOnScreen(){
        if(currentCard > 0){
            currentCard--;
            cardText.setText(getWordCardFromDeck().getWordSide());
        }
        else{
            currentCard = numOfCardsInDeck;
            cardText.setText(getWordCardFromDeck().getWordSide());
        }
    }

    public void changeViewOnScreenClick(){
        if(displayWordSide){
            cardText.setText(getWordCardFromDeck().getDefinitionSide());
            displayWordSide = false;
        }
        else{
            cardText.setText(getWordCardFromDeck().getWordSide());
            displayWordSide = true;
        }
    }
    public void setTextColorBasedOnUserPreference(){
        String valueOfTextColor = sharedPreferences.getString(ConstantsForPreferenceFile.PREF_TEXT_COLOR_KEY, "");
        Log.i("textPref", valueOfTextColor);
        if(valueOfTextColor.equals(getString(R.string.color_black)) || valueOfTextColor.isEmpty()){
            cardText.setTextColor(getResources().getColor(R.color.black));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_blue))){
            cardText.setTextColor(getResources().getColor(R.color.blue_base));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_purple))){
            cardText.setTextColor(getResources().getColor(R.color.purple_dark));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_red))){
            cardText.setTextColor(getResources().getColor(R.color.red_base));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_orange))){
            cardText.setTextColor(getResources().getColor(R.color.orange_base));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_yellow))){
            cardText.setTextColor(getResources().getColor(R.color.yellow_base));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_green))){
            cardText.setTextColor(getResources().getColor(R.color.green_base));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_white))){
            cardText.setTextColor(getResources().getColor(R.color.white));
        }

    }

    public void setBackgroundColorBasedOnUserPreference(){
        String valueOfTextColor = sharedPreferences.getString(ConstantsForPreferenceFile.PREF_BACKGROUND_COLOR_KEY, "");
        Log.i("textPref", valueOfTextColor);
        if(valueOfTextColor.equals(getString(R.string.color_black)) || valueOfTextColor.isEmpty()){
            cardText.setBackgroundColor(getResources().getColor(R.color.black));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_blue))){
            cardText.setBackgroundColor(getResources().getColor(R.color.blue_base));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_purple))){
            cardText.setBackgroundColor(getResources().getColor(R.color.purple_dark));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_red))){
            cardText.setBackgroundColor(getResources().getColor(R.color.red_base));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_orange))){
            cardText.setBackgroundColor(getResources().getColor(R.color.orange_base));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_yellow))){
            cardText.setBackgroundColor(getResources().getColor(R.color.yellow_base));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_green))){
            cardText.setBackgroundColor(getResources().getColor(R.color.green_base));
        }
        else if(valueOfTextColor.equals(getString(R.string.color_white))){
            cardText.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }
}