package com.lamcreations.scaffold.common.activities;


import com.lamcreations.scaffold.R;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public abstract class ToolbarActivity extends BaseActivity {

    protected CharSequence mActionBarTitle;
    protected Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayoutResId());
        setupActionBar();
    }

    protected CharSequence getActionBarTitle() {
        if(mActionBarTitle == null){
            mActionBarTitle = getTitle();
        }
        return mActionBarTitle;
    }

    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            mToolbar = (Toolbar) findViewById(R.id.scaffold_toolbar);
            setSupportActionBar(mToolbar);
            actionBar = getSupportActionBar();
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getActionBarTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home || item.getItemId() == R.id.home){
            onUpNavigation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract void onUpNavigation();

    @LayoutRes
    protected abstract int getActivityLayoutResId();
}
