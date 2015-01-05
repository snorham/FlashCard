package com.example.bfinerocks.flashcard.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.constants.ConstantsForReference;
import com.example.bfinerocks.flashcard.interfaces.FragmentTransitionInterface;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class SignInFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getActivity() instanceof FragmentTransitionInterface) {
            View rootview = inflater.inflate(R.layout.fragment_signin, container, false);
            final EditText userNameEntry = (EditText) rootview.findViewById(R.id.edtxt_user_name_entry);
            Button signInDone = (Button) rootview.findViewById(R.id.btn_sign_in_done);
            signInDone.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userNameEntered = userNameEntry.getText().toString().trim();
                    generateSharedPrefWithUserName(userNameEntered);
                    FragmentTransitionInterface fti = (FragmentTransitionInterface) getActivity();
                    HomepageFragment homepageFragment = HomepageFragment.newInstance(userNameEntered);
                    fti.onFragmentChange(homepageFragment);
                }
            });
            return rootview;
        }
        else{
                throw new IllegalStateException("SignInFragment must be created with an activity that implements FragmentTransitionInterface");
            }
    }

    public void generateSharedPrefWithUserName(String usernameEntered){
        SharedPreferences namePreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Editor prefEditor = namePreference.edit();
        prefEditor.putString(ConstantsForReference.USER_NAME_PREFERENCE, usernameEntered);
        prefEditor.apply();
    }
}
