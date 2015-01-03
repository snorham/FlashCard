package com.example.bfinerocks.flashcard.fragments;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.bfinerocks.flashcard.R;

/**
 * Created by BFineRocks on 1/2/15.
 */
public class FlashCardSettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
/*        if(key.equals(ConstantsForPreferenceFile.PREF_AUTO_DEF_KEY)){
            Preference autoDefPref = findPreference(key);
            autoDefPref.setSummary();
        }*/
    }
}
