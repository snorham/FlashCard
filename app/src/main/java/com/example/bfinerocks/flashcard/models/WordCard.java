package com.example.bfinerocks.flashcard.models;

import android.os.Parcel;

/**
 * Created by BFineRocks on 12/23/14.
 */
public class WordCard implements android.os.Parcelable {

    private String wordSide;
    private String definitionSide;

    public WordCard(String word){
        this.wordSide = word;
    }

    public String getWordSide() {
        return wordSide;
    }

    public void setWordSide(String wordSide) {
        this.wordSide = wordSide;
    }

    public String getDefinitionSide() {
        return definitionSide;
    }

    public void setDefinitionSide(String definitionSide) {
        this.definitionSide = definitionSide;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.wordSide);
        dest.writeString(this.definitionSide);
    }

    private WordCard(Parcel in) {
        this.wordSide = in.readString();
        this.definitionSide = in.readString();
    }

    public static final Creator<WordCard> CREATOR = new Creator<WordCard>() {
        public WordCard createFromParcel(Parcel source) {
            return new WordCard(source);
        }

        public WordCard[] newArray(int size) {
            return new WordCard[size];
        }
    };
}
