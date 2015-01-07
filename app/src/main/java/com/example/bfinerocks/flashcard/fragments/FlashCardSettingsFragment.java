package com.example.bfinerocks.flashcard.fragments;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.constants.ConstantsForPreferenceFile;
import com.example.bfinerocks.flashcard.tools.FlashCardTools;

/**
 * Created by BFineRocks on 1/2/15.
 */
public class FlashCardSettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        FlashCardTools.enableNavigationalHomeButton(getActivity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new FlashCardSettingsFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(ConstantsForPreferenceFile.PREF_TEXT_COLOR_KEY)){
            Preference autoDefPref = findPreference(key);
            autoDefPref.setSummary(sharedPreferences.getString(key, " "));
        }
    }

}
