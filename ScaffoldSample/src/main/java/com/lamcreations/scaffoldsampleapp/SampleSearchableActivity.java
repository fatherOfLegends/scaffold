package com.lamcreations.scaffoldsampleapp;


import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.lamcreations.scaffold.common.activities.SearchableActivity;


public class SampleSearchableActivity extends SearchableActivity {

    @Override
    protected void setupSearchEditText() {
        super.setupSearchEditText();
        mSearchEditText.setHint(R.string.search_scaffold);
        mSearchEditText.setTextColor(getResources().getColor(R.color.window_background));
        mSearchEditText.setHintTextColor(getResources().getColor(R.color.window_background));
        mSearchEditText.setBackground(null);
    }

    @Override
    protected void performSearch(String query) {
        Toast.makeText(this, "Perform Search", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.frame_layout;
    }

    @Override
    protected boolean setupFab() {
        return false;
    }

    @Override
    protected void onUpNavigation() {
        ActivityCompat.finishAfterTransition(this);
    }
    @Override
    protected boolean performSearchOnQueryChange() {
        return false;
    }

    @Override
    protected boolean showVoiceSearch() {
        return true;
    }

    @Override
    protected boolean showClearSearch() {
        return true;
    }
}
