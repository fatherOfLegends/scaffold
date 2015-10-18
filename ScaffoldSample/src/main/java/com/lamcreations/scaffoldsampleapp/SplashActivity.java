package com.lamcreations.scaffoldsampleapp;

import com.lamcreations.scaffold.common.activities.SplashScreenActivity;
import com.lamcreations.scaffold.common.utils.DisplayUtils;

import android.content.Intent;
import android.os.Bundle;


public class SplashActivity extends SplashScreenActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int padding = DisplayUtils.screenPixels(this, 10);
        mSplashImage.setPadding(padding, padding, padding, padding);
        setSplashImage(R.drawable.scaffold);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }
}
