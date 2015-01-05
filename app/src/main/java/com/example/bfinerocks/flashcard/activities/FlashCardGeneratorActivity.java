package com.example.bfinerocks.flashcard.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.constants.ConstantsForReference;
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
        Deck deckToDisplay = bundle.getParcelable(ConstantsForReference.DECK_TO_FLASH);
        FragmentManager fragmentManager = getFragmentManager();
        FlashCardGeneratorFragment flashCardGeneratorFragment = FlashCardGeneratorFragment.newInstance(deckToDisplay);
        fragmentManager.beginTransaction()
                .add(R.id.container, flashCardGeneratorFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent com.example.bfinerocks.flashcard.activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.flash_stop) {
            Intent stopIntent = new Intent(this, MainActivity.class);
            startActivity(stopIntent);
        }

        return super.onOptionsItemSelected(item);
    }

}
