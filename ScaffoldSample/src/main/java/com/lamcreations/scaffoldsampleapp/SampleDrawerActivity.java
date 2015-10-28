package com.lamcreations.scaffoldsampleapp;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;

import com.lamcreations.scaffold.common.activities.DrawerActivity;


public class SampleDrawerActivity extends DrawerActivity {

    @Override
    protected Fragment getLeftStartDrawerFragment() {
        return new SampleNavigationDrawerFragment();
    }

    @Override
    protected Fragment getRightEndDrawerFragment() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(R.id.scaffold_content, new EarthquakesFragment());
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.frame_layout;
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
