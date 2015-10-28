package com.lamcreations.scaffoldsampleapp;

import com.lamcreations.scaffold.common.activities.ToolbarActivity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;


public class MainActivity extends ToolbarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(R.id.scaffold_content, new PossibilitiesFragment());
    }

    @Override
    protected void setupActionBar() {
        super.setupActionBar();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }
    }

    @Override
    protected void onUpNavigation() {
        finish();
    }

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.scaffold_activity_toolbar;
    }
}
