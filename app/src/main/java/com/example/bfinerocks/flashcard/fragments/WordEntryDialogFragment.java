package com.example.bfinerocks.flashcard.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.constants.ConstantsForPreferenceFile;
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

    private String wordToDefine;
    private WordCard wordCard;
    private EditText wordText;

    public interface WordCardCreatorDialogInterface {
        public void positiveClickNextWordCard();
        public void negativeClickNoMoreCards();
        public void wordCardCreated(WordCard wordCard);
    }

    private WordCardCreatorDialogInterface wordCardInterface;

    public static WordEntryDialogFragment newInstance(WordCardCreatorDialogInterface fragmentShowingDialog){
        WordEntryDialogFragment wordEntryDialogFragment = new WordEntryDialogFragment();
        wordEntryDialogFragment.wordCardInterface = fragmentShowingDialog;
      return wordEntryDialogFragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layoutView = inflater.inflate(R.layout.fragment_dialogue_word_entry, null);
        alertBuilder.setTitle(R.string.dialog_header);
        alertBuilder.setView(layoutView);
        wordText = (EditText) layoutView.findViewById(R.id.word_to_define);
        alertBuilder.setPositiveButton(R.string.btn_next_word, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                wordToDefine = wordText.getText().toString().trim();
                if(userPrefForAutoDefinition()){
                    sendNewWordCardWithWithoutDefinition(wordToDefine);
                }
                else{
                    sendNewWordCardToHostFragment(wordToDefine);
                }
                //wordCardInterface.positiveClickNextWordCard();
                recreateDialogOnPositiveClick();
                //todo handle pos click to restart frag

            }
        });
        alertBuilder.setNegativeButton(R.string.btn_done, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                wordToDefine = wordText.getText().toString().trim();
                if(userPrefForAutoDefinition()){
                    sendNewWordCardWithWithoutDefinition(wordToDefine);
                }
                else{
                    sendNewWordCardToHostFragment(wordToDefine);
                }
                wordCardInterface.negativeClickNoMoreCards(); //todo can go use dismiss call

                //todo handle neg click to dismiss frag
            }
        });
        return alertBuilder.create();
    }

    public void sendNewWordCardToHostFragment(String wordToDefine){
        final WordCard wordCard = new WordCard(wordToDefine);
        updateWordCardWithDefinitionFromDictionary(wordCard);
    }

    public void updateWordCardWithDefinitionFromDictionary(final WordCard wordCard){
        WordNikAPI wordNikAPI = WordNikAPI.getWordNikAPI();
        wordNikAPI.searchWordDefinition(wordToDefine, new WordNikAPIInterface() {
            @Override
            public void onWordNikCallSuccess(JSONObject jsonObject) {
                DefinitionParsing parser = new DefinitionParsing();
                try {
                    String definition = parser.parseDefinitionFromDictionaryAPI(jsonObject);
                    wordCard.setDefinitionSide(definition);
                    wordCardInterface.wordCardCreated(wordCard);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onWordNikCallFailure() {
                wordCard.setNoDefinitionFound();
                wordCardInterface.wordCardCreated(wordCard);
            }
        });
    }

    public void sendNewWordCardWithWithoutDefinition(String wordEntered){
        WordCard wordCardWithOutDef = new WordCard(wordEntered);
        wordCardInterface.wordCardCreated(wordCardWithOutDef);
    }

    public boolean userPrefForAutoDefinition(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return sharedPreferences.getBoolean(ConstantsForPreferenceFile.PREF_AUTO_DEF_KEY, false);
    }

    public void recreateDialogOnPositiveClick(){
        WordEntryDialogFragment wordEntryDialogFragment = newInstance(wordCardInterface);
        wordEntryDialogFragment.show(getFragmentManager(), "show");
    }

}
