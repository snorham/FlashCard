package com.example.bfinerocks.flashcard;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class SignInFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.signin_fragment, container, false);
        EditText userNameEntry = (EditText) rootview.findViewById(R.id.edtxt_user_name_entry);
        return rootview;
    }
}
