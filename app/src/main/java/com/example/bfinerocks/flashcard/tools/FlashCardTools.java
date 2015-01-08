package com.example.bfinerocks.flashcard.tools;

import android.app.ActionBar;
import android.app.Activity;

/**
 * Created by Borham on 1/7/15.
 */
public class FlashCardTools {

    public static void enableNavigationalHomeButton(Activity parent, boolean enabled) {
        ActionBar ab = parent.getActionBar();
        if (ab != null){
            ab.setHomeButtonEnabled(enabled);
            ab.setDisplayHomeAsUpEnabled(enabled);
        }
    }
}
