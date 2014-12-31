package com.example.bfinerocks.flashcard.activities;

import android.app.Activity;
import android.os.Bundle;

import com.example.bfinerocks.flashcard.R;

/**
 * Created by BFineRocks on 12/30/14.
 */
public class WordCardGeneratorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_generator);
        getFragmentManager().beginTransaction()
                .add(R.id.container, new FlashCard)
                .commit();
    }
}
