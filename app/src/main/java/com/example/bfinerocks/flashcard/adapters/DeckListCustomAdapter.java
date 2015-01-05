package com.example.bfinerocks.flashcard.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.models.Deck;

import java.util.List;

/**
 * Created by BFineRocks on 12/30/14.
 */
public class DeckListCustomAdapter extends ArrayAdapter<Deck> {

    private static class ViewHolder{
        TextView deckName;
    }

    private int layoutResource;

    public DeckListCustomAdapter(Context context, int resource, List<Deck> objects) {
        super(context, resource, objects);
        this.layoutResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("viewCalled", "view Called");
        ViewHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.deckName = (TextView) convertView.findViewById(R.id.deck_name);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        Deck deck = getItem(position);
        if(deck != null){
            holder.deckName.setText(deck.getDeckName());
        }
        return convertView;
    }




}
