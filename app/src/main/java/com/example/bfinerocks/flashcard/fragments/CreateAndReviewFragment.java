package com.example.bfinerocks.flashcard.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.models.Deck;
import com.example.bfinerocks.flashcard.models.WordCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BFineRocks on 12/22/14.
 */
public class CreateAndReviewFragment extends Fragment {

    private static final String DIALOG_FRAG_TAG = "WordEntryDialog";
    private List<WordCard> listOfWordCards;
    private ListAdapter listAdapter;
    private ListView listView;
    private Deck myDeck;


    public static CreateAndReviewFragment newInstance(){
        return new CreateAndReviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_and_review_fragment, container, false);
        EditText deckNameEditText = (EditText) rootView.findViewById(R.id.edtxt_list_title);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        Button saveListButton = (Button) rootView.findViewById(R.id.save_button);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listOfWordCards = new ArrayList<WordCard>();

        super.onViewCreated(view, savedInstanceState);
    }

    public void showWordEntryDialogFragment(){
        DialogFragment wordEntryFragment = new WordEntryDialogFragment();
        wordEntryFragment.show(getActivity().getFragmentManager(), DIALOG_FRAG_TAG);
    }

    public static class WordEntryDialogFragment extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            alertBuilder.setTitle(R.string.dialog_header);
            alertBuilder.setView(inflater.inflate(R.layout.fragment_dialogue_word_entry, null));
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
    }
}
