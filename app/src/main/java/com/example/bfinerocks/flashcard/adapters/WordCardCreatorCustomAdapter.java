package com.example.bfinerocks.flashcard.adapters;

import android.content.Context;
import android.util.Log;
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

    private static class ViewHolder{
        EditText wordEntered;
        EditText wordDefinition;
    }

    private int layoutResource;
    private WordCard wordCard;

    public WordCardCreatorCustomAdapter(Context context, int resource, List<WordCard> objects) {
        super(context, resource, objects);
        this.layoutResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView == null) {
            convertView = convertTheView(holder, parent);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        wordCard = getItem(position);
        if(wordCard != null){
            Log.i("wordCard", "Word card not null");
            holder.wordEntered.setText(wordCard.getWordSide());
            holder.wordDefinition.setText(wordCard.getDefinitionSide());
        }
        return convertView;
    }

    private View convertTheView(final ViewHolder holder, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutResource, parent, false);
        holder.wordEntered = (EditText) view.findViewById(R.id.word);
/*        holder.wordEntered.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("wordCard", "text change called");
                wordCard.setWordSide(holder.wordDefinition.getText().toString());
            }
        });*/
        holder.wordDefinition = (EditText) view.findViewById(R.id.definition);
/*        holder.wordDefinition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                wordCard.setDefinitionSide(holder.wordDefinition.getText().toString());
            }
        });*/
        view.setTag(holder);
        return view;
    }


}
