package com.example.bfinerocks.flashcard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.bfinerocks.flashcard.R;
import com.example.bfinerocks.flashcard.models.WordCard;

import java.util.List;

/**
 * Created by BFineRocks on 12/28/14.
 */
public class WordCardCreatorCustomAdapter extends ArrayAdapter<WordCard> {

    static class ViewHolder{
        EditText wordEntered;
        EditText wordDefinition;
    }

    private int layoutResource;
    private List<WordCard> wordCardObjects;

    public WordCardCreatorCustomAdapter(Context context, int resource, List<WordCard> objects) {
        super(context, resource, objects);
        this.layoutResource = resource;
        this.wordCardObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.wordEntered = (EditText) convertView.findViewById(R.id.word);
            holder.wordDefinition = (EditText) convertView.findViewById(R.id.definition);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        WordCard wordCard = wordCardObjects.get(position);
        if(wordCard != null){
            holder.wordEntered.setText(wordCard.getWordSide());
            holder.wordDefinition.setText(wordCard.getDefinitionSide());
        }
        return convertView;
    }

}
