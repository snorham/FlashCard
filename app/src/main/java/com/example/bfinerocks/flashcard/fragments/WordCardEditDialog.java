package com.example.bfinerocks.flashcard.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
    private WordCard wordCardToEdit;
    private WordCardEditDialogInterface wordCardEditDialogInterface;

    public interface WordCardEditDialogInterface{
        public void wordCardEditedByUser(WordCard wordCard);
    }

    public static WordCardEditDialog newInstance(WordCard wordCardToEdit, WordCardEditDialogInterface wordCardEditDialogInterfaceSent){
        Bundle bundle = new Bundle();
        bundle.putParcelable("bundle", wordCardToEdit);
        WordCardEditDialog wordCardEditDialog = new WordCardEditDialog();
        wordCardEditDialog.setArguments(bundle);
        wordCardEditDialog.wordCardEditDialogInterface = wordCardEditDialogInterfaceSent;
        return wordCardEditDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        getWordCardFromBundle();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_edit_word_card, null);
        builder.setTitle(R.string.dialog_edit_header);
        final EditText wordEditField = (EditText) layout.findViewById(R.id.dialog_word_edit_field);
        wordEditField.setText(wordToEdit);
        final EditText definitionEditField = (EditText) layout.findViewById(R.id.dialog_edit_def_field);
        definitionEditField.setText(definitionToEdit);
        builder.setView(layout);
        builder.setPositiveButton(R.string.btn_edit_card, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                wordToEdit = wordEditField.getText().toString();
                definitionToEdit = definitionEditField.getText().toString();
                wordCardToEdit.setWordSide(wordToEdit);
                wordCardToEdit.setDefinitionSide(definitionToEdit);
                wordCardEditDialogInterface.wordCardEditedByUser(wordCardToEdit);
            }
        });
        return builder.create();
    }

    public void getWordCardFromBundle(){
        WordCard wordCard = getArguments().getParcelable("bundle");
        if(wordCard != null) {
            wordToEdit = wordCard.getWordSide();
            definitionToEdit = wordCard.getDefinitionSide();
            wordCardToEdit = wordCard;
        }
    }
}
