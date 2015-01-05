package com.example.bfinerocks.flashcard.fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.bfinerocks.flashcard.R;

/**
 * Created by BFineRocks on 1/5/15.
 */
public class WordCardEditDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_edit_word_card, null);
        builder.setTitle(R.string.dialog_edit_header);
        builder.setView(layout);
    }
}
