package com.example.bfinerocks.flashcard.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by BFineRocks on 1/2/15.
 */
public class FlashCardSettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource();
    }
}
