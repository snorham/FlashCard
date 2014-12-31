package com.example.bfinerocks.flashcard.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.fragments.FlashCardGenerator;

/**
 * Created by BFineRocks on 12/30/14.
 */
public class WordCardGeneratorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_generator);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, new FlashCardGenerator())
                .commit();
    }
}
