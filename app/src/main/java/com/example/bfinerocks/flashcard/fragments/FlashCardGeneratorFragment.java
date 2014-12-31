package com.example.bfinerocks.flashcard.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.models.Deck;

/**
 * Created by BFineRocks on 12/30/14.
 */
public class FlashCardGeneratorFragment extends Fragment implements OnClickListener {

    private TextView cardText;
    private Button backButton;
    private Button nextButton;
    private Deck deck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        deck = intent.getParcelableExtra("deckSelected");
        Log.i("deck", intent.toString());
        View rootView = inflater.inflate(R.layout.fragment_card_view, container, false);
        cardText = (TextView) rootView.findViewById(R.id.card_text);
        cardText.setOnClickListener(this);
        backButton = (Button) rootView.findViewById(R.id.btn_back);
        backButton.setOnClickListener(this);
        nextButton = (Button) rootView.findViewById(R.id.btn_next);
        nextButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_text:
                break;
            case R.id.btn_back:
                break;
            case R.id.btn_next:
                break;
        }
    }
}
