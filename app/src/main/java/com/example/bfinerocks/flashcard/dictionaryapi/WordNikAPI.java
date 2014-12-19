package com.example.bfinerocks.flashcard.dictionaryapi;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
            try {
                return getJSONObjectFromAPI(uris[0]);
            } catch (IOException e) {
                return null;
            } catch (JSONException e) {
               return null;
            }
        }

        private JSONObject getJSONObjectFromAPI(Uri uri) throws IOException, JSONException{
            URL url = new URL((uri).toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            return new JSONObject(stringBuilder.toString());
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
           if(jsonObject != null){
               mWordNikAPIInterface //todo setup interface
           }
            else{
               //interface
           }
        }
    }
}
