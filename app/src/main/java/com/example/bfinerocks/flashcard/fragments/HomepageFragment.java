package com.example.bfinerocks.flashcard.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bfinerocks.flashcard.R;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class HomepageFragment extends Fragment {

    private static final String SAVED_DECK_KEY = "saved_deck_of_cards";

    public static HomepageFragment newInstance(List<Deck> listOfSavedDecks){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SAVED_DECK_KEY, listOfSavedDecks);
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

        return rootView;
    }
}
