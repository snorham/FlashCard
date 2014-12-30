package com.example.bfinerocks.flashcard.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bfinerocks.flashcard.models.Deck;

import java.util.List;

/**
 * Created by BFineRocks on 12/30/14.
 */
public class DeckListCustomAdapter extends ArrayAdapter<Deck> {

    private static class ViewHolder{
        TextView deckName;
    }

    public DeckListCustomAdapter(Context context, int resource, List<Deck> objects) {
        super(context, resource, objects);
    }




}
