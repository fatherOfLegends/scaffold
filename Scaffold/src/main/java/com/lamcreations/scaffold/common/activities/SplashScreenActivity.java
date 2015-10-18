package com.lamcreations.scaffold.common.activities;

import com.lamcreations.scaffold.R;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;


public abstract class SplashScreenActivity extends BaseActivity {

    protected ImageView mSplashImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mSplashImage = (ImageView) findViewById(R.id.splash_image);
    }

    public void setSplashImage(@DrawableRes int resId){
        mSplashImage.setImageResource(resId);
    }
}
