package com.lamcreations.scaffoldsampleapp;


import com.lamcreations.scaffold.navigation.activities.TabActivity;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;


public class SampleTabActivity extends TabActivity {

    @Override
    protected int getTabMode() {
        return TabLayout.MODE_SCROLLABLE;
    }

    @Override
    protected PagerAdapter getPagerAdapter() {
        return new SampleTabPagerAdapter(getSupportFragmentManager());
    }

    @Override
    protected int getInitialTabPosition() {
        return 0;
    }

    @Override
    protected boolean setupFab() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mContentView, "Do Something Awesome!", Snackbar.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    @Override
    protected void onUpNavigation() {
        ActivityCompat.finishAfterTransition(this);
    }
}
