package com.lamcreations.scaffoldsampleapp;


import com.lamcreations.scaffold.common.activities.ToolbarActivity;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;


public class SampleToolbarActivity extends ToolbarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(R.id.content, new EarthquakesFragment());
    }

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.activity_toolbar;
    }

    @Override
    protected void onUpNavigation() {
        ActivityCompat.finishAfterTransition(this);
    }
}
