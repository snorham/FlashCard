package com.example.bfinerocks.flashcard.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.dictionaryapi.DefinitionParsing;
import com.example.bfinerocks.flashcard.dictionaryapi.WordNikAPI;
import com.example.bfinerocks.flashcard.dictionaryapi.WordNikAPIInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class HomepageFragment extends Fragment {

    private static final String USER_FIREBASE_REFERENCE = "firebase_user_ref";

    public static HomepageFragment newInstance(String user){
        Bundle bundle = new Bundle();
        bundle.putString(USER_FIREBASE_REFERENCE, user);
        HomepageFragment homepageFragment = new HomepageFragment();
        homepageFragment.setArguments(bundle);
        return homepageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_homepage, container, false);
        TextView welcomeUser = (TextView) rootView.findViewById(R.id.homepage_welcome);
        ListView listOfDecksSaved = (ListView) rootView.findViewById(R.id.homepage_listView);
        TextView linkToCreateNewDeck = (TextView) rootView.findViewById(R.id.txt_create_new_deck);
        WordNikAPI wordNikAPI = WordNikAPI.getWordNikAPI();
        wordNikAPI.searchWordDefinition("shoe", new WordNikAPIInterface() {
            @Override
            public void onWordNikCallSuccess(JSONObject jsonObject) {
                Log.i("interfaceSuccessUI" , "working");
                DefinitionParsing definitionParsing = new DefinitionParsing();
                try {
                    definitionParsing.parseDefinitionFromDictionaryAPI(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onWordNikCallFailure() {

            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUpdatedSavedDeckFromFirebase();
    }

    public void getUpdatedSavedDeckFromFirebase(){
/*        String userReference = getArguments().getString(USER_FIREBASE_REFERENCE);
        if(userReference != null){
            FirebaseStorage firebaseStorage = new FirebaseStorage();
            firebaseStorage.createFirebaseReferenceWithUserNameForReference(userReference);
            Firebase ref = firebaseStorage.getReferenceWithUser();
          //todo complete this method when firebasestorage set up
        }*/
    }
}
