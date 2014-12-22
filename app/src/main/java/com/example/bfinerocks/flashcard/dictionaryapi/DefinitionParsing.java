package com.example.bfinerocks.flashcard.dictionaryapi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BFineRocks on 12/22/14.
 */
public class DefinitionParsing {
    //todo change to String return if working
    public void parseDefinitionFromDictionaryAPI(JSONObject jsonObject) throws JSONException{
        String wordDefinition = jsonObject.getString("text");
        Log.i("parsing", wordDefinition);
    }
}
