package com.example.bfinerocks.flashcard.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.constants.ConstantsForReference;
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
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        String userName = sharedPreferences.getString(ConstantsForReference.USER_NAME_PREFERENCE, "");
        if(userName.isEmpty()){
            onFragmentChange(new SignInFragment());
        }
        else{

            HomepageFragment homepageFragment = HomepageFragment.newInstance(userName);
            onFragmentChange(homepageFragment);
        }

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
