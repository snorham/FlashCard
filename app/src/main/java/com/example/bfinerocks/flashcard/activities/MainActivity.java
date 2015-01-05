package com.example.bfinerocks.flashcard.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.fragments.FlashCardSettingsFragment;
import com.example.bfinerocks.flashcard.fragments.HomepageFragment;
import com.example.bfinerocks.flashcard.fragments.SignInFragment;
import com.example.bfinerocks.flashcard.interfaces.FragmentTransitionInterface;
import com.firebase.client.Firebase;


public class MainActivity extends Activity implements FragmentTransitionInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(getApplicationContext());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(!sharedPreferences.contains(SignInFragment.USER_NAME_PREFERENCE)){
            onFragmentChange(new SignInFragment());
        }
        else{
            HomepageFragment homepageFragment = HomepageFragment.newInstance("bfine"); //todo get user name from pref file
            onFragmentChange(homepageFragment);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent com.example.bfinerocks.flashcard.activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new FlashCardSettingsFragment())
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentChange(Fragment fragmentToTransitionTo) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragmentToTransitionTo)
                .addToBackStack(null)
                .commit();
    }
}
