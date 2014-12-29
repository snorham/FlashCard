package com.example.bfinerocks.flashcard.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.interfaces.FragmentTransitionInterface;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class SignInFragment extends Fragment {
    public static final String USER_NAME_PREFENCE_FILE = "userNamePreferenceFile";
    public static final String USER_NAME_PREFERENCE = "preferredUser";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.signin_fragment, container, false);
        final EditText userNameEntry = (EditText) rootview.findViewById(R.id.edtxt_user_name_entry);
        Button signInDone = (Button) rootview.findViewById(R.id.btn_sign_in_done);
        signInDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameEntered = userNameEntry.getText().toString().trim();
                generateSharedPrefWithUserName(userNameEntered);
                FragmentTransitionInterface fti = (FragmentTransitionInterface) getActivity();
                HomepageFragment homepageFragment = HomepageFragment.newInstance("user");//todo think about what we are passing
                fti.onFragmentChange(homepageFragment, "homepage");                     //will not pass "user" this is a placeholder
            }
        });
        return rootview;
    }

    public void generateSharedPrefWithUserName(String usernameEntered){
        SharedPreferences namePreference = getActivity().getSharedPreferences(USER_NAME_PREFENCE_FILE, Context.MODE_PRIVATE);
        Editor prefEditor = namePreference.edit();
        prefEditor.putString(USER_NAME_PREFERENCE, usernameEntered);
        prefEditor.apply();
    }
}
