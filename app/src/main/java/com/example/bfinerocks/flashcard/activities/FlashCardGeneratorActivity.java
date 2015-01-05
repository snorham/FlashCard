package com.example.bfinerocks.flashcard.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.fragments.FlashCardGeneratorFragment;
import com.example.bfinerocks.flashcard.models.Deck;
import com.firebase.client.Firebase;

/**
 * Created by BFineRocks on 12/30/14.
 */
public class FlashCardGeneratorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getApplicationContext());
        setContentView(R.layout.activity_flash_card_generator);


        Bundle bundle = getIntent().getExtras();

        Log.i("deck", bundle.getParcelable("com.example.bfinerocks.flashcard.models.Deck").toString());
        Deck deckToDisplay = bundle.getParcelable("com.example.bfinerocks.flashcard.models.Deck");
        FragmentManager fragmentManager = getFragmentManager();
        FlashCardGeneratorFragment flashCardGeneratorFragment = FlashCardGeneratorFragment.newInstance(deckToDisplay);
        fragmentManager.beginTransaction()
                .add(R.id.container, flashCardGeneratorFragment)
                .commit();
    }
}
