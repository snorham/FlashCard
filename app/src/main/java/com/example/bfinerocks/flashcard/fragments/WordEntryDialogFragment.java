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
import com.example.bfinerocks.flashcard.dictionaryapi.DefinitionParsing;
import com.example.bfinerocks.flashcard.dictionaryapi.WordNikAPI;
import com.example.bfinerocks.flashcard.interfaces.WordNikAPIInterface;
import com.example.bfinerocks.flashcard.models.WordCard;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BFineRocks on 12/29/14.
 */
public class WordEntryDialogFragment extends DialogFragment {

    public interface WordCardCreatorDialogInterface extends com.example.bfinerocks.flashcard.interfaces.WordCardCreatorDialogInterface {
        public void onDialogClick(String wordEntered);
    }


    com.example.bfinerocks.flashcard.interfaces.WordCardCreatorDialogInterface wordCardInterface;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layoutView = inflater.inflate(R.layout.fragment_dialogue_word_entry, null, false);
        alertBuilder.setTitle(R.string.dialog_header);
        alertBuilder.setView(inflater.inflate(R.layout.fragment_dialogue_word_entry, null));
        EditText wordText = (EditText) layoutView.findViewById(R.id.word_to_define);
        String wordToDefine = wordText.getText().toString().trim();
        alertBuilder.setPositiveButton(R.string.btn_next_word, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertBuilder.setNegativeButton(R.string.btn_done, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){

            }
        });
        return alertBuilder.create();
    }

    public WordCard getWordDefinition(String wordToDefine){
        final WordCard wordCard = new WordCard(wordToDefine);
        WordNikAPI wordNikAPI = WordNikAPI.getWordNikAPI();
        wordNikAPI.searchWordDefinition(wordToDefine, new WordNikAPIInterface() {
            @Override
            public void onWordNikCallSuccess(JSONObject jsonObject) {
                DefinitionParsing parser = new DefinitionParsing();
                try {
                    String definition = parser.parseDefinitionFromDictionaryAPI(jsonObject);
                    wordCard.setDefinitionSide(definition);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onWordNikCallFailure() {
                String definitionNotFound = "No definition was found";
                wordCard.setDefinitionSide(definitionNotFound);
            }
        });
        return wordCard;
    }


}
