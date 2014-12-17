package com.example.bfinerocks.flashcard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.bfinerocks.flashcard.R;

/**
 * Created by BFineRocks on 12/17/14.
 */
public class SplashActivity extends Activity {
    private final int SPLASH_SCREEN_DISPLAY_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_SCREEN_DISPLAY_TIME);
    }
}
