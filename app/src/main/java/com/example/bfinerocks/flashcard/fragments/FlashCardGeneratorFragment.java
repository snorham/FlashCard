package com.example.bfinerocks.flashcard.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.models.Deck;
import com.example.bfinerocks.flashcard.models.WordCard;

/**
 * Created by BFineRocks on 12/30/14.
 */
public class FlashCardGeneratorFragment extends Fragment implements OnClickListener {

    private TextView cardText;
    private Button backButton;
    private Button nextButton;
    private Deck deck;
    private int currentCard;
    private int deckSize;
    private boolean displayWordSide;

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
        deckSize = deck.getMyDeck().size();
        cardText = (TextView) rootView.findViewById(R.id.card_text);
        cardText.setOnClickListener(this);
        backButton = (Button) rootView.findViewById(R.id.btn_back);
        backButton.setOnClickListener(this);
        nextButton = (Button) rootView.findViewById(R.id.btn_next);
        nextButton.setOnClickListener(this);
        changeCardOnScreen();
        currentCard = 0;
        displayWordSide = true;
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_text:
                changeViewOnScreenClick();
                break;
            case R.id.btn_back:

                break;
            case R.id.btn_next:
                changeCardOnScreen();
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

    public void changeCardOnScreen(){
        if(currentCard < deckSize) {
            cardText.setText(getWordCardFromDeck().getWordSide());
            currentCard++;
        }
        else{
            currentCard = 0;
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
}
