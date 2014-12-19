package com.example.bfinerocks.flashcard.dictionaryapi;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by BFineRocks on 12/18/14.
 */
public class WordNikAPI {

    private static final String WORDNIK_ROOT_URL = "api.wordnik.com/v4/word.json";
    private static final String WORDNIK_DEFINITION_PATH = "definition";
    private static final String WORDNIK_QUERY_LIMIT = "limit";
    private static final String WORDNIK_QUERY_RELATED = "includeRelated";
    private static final String WORDNIK_QUERY_CANONICAL = "useCanonical";
    private static final String WORDNIK_QUERY_TAGS = "includeTags";
    private static final String WORDNIK_QUERY_API = "api_key";
    private static final String WORDNIK_API_KEY = "6aa015c0d84b01a6c205f6848a6dea42bcb91d757d4341dde";

    private static WordNikAPI sWordNikAPI;

    public static WordNikAPI getWordNikAPI(){
        if(sWordNikAPI != null){
            sWordNikAPI = new WordNikAPI();
        }
        return sWordNikAPI;
    }

    private WordNikAPI(){

    }

    public void searchWordDefinition(String wordToDefine, WordNikAPIInterface wordNikAPIInterface){

        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority(WORDNIK_ROOT_URL)
                .appendPath(wordToDefine)
                .appendPath(WORDNIK_DEFINITION_PATH)
                .appendQueryParameter(WORDNIK_QUERY_LIMIT, "1")
                .appendQueryParameter(WORDNIK_QUERY_RELATED, "false")
                .appendQueryParameter(WORDNIK_QUERY_CANONICAL, "false")
                .appendQueryParameter(WORDNIK_QUERY_TAGS, "false")
                .appendQueryParameter(WORDNIK_QUERY_API, WORDNIK_API_KEY)
                .build();
    }

    private class GetDefinitionInBackground extends AsyncTask<Uri, Void, JSONObject>{

        private WordNikAPIInterface mWordNikAPIInterface;

        private GetDefinitionInBackground(WordNikAPIInterface wordNikAPIInterface){
            this.mWordNikAPIInterface = wordNikAPIInterface;
        }

        @Override
        protected JSONObject doInBackground(Uri... uris) {
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }
    }
}
