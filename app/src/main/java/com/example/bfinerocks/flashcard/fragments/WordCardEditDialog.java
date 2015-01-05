package com.example.bfinerocks.flashcard.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.models.WordCard;

/**
 * Created by BFineRocks on 1/5/15.
 */
public class WordCardEditDialog extends DialogFragment {

    private String wordToEdit;
    private String definitionToEdit;

    public static WordCardEditDialog newInstance(WordCard wordCardToEdit){
        Bundle bundle = new Bundle();
        bundle.putParcelable("bundle", wordCardToEdit);
        WordCardEditDialog wordCardEditDialog = new WordCardEditDialog();
        wordCardEditDialog.setArguments(bundle);
        return wordCardEditDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        getWordCardFromBundle();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_edit_word_card, null);
        builder.setTitle(R.string.dialog_edit_header);
        EditText wordEditField = (EditText) layout.findViewById(R.id.dialog_word_edit_field);
        wordEditField.setText(wordToEdit);
        EditText definitionEditField = (EditText) layout.findViewById(R.id.dialog_edit_def_field);
        definitionEditField.setText(definitionToEdit);
        builder.setView(layout);
        return builder.create();
    }

    public void getWordCardFromBundle(){
        WordCard wordCard = getArguments().getParcelable("bundle");
        if(wordCard != null) {
            wordToEdit = wordCard.getWordSide();
            definitionToEdit = wordCard.getDefinitionSide();
        }
    }
}
