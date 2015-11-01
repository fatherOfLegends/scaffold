/*
 * Copyright (C) 2015 LAM Creations
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lamcreations.scaffold.common.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import com.lamcreations.scaffold.R;


public abstract class DrawerActivity extends CoordinatorActivity
        implements DrawerLayout.DrawerListener {

    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.scaffold_activity_drawer;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setupDrawerToggle();
        addLeftStartDrawerFragment();
        addRightEndDrawerFragment();
    }

    protected void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.scaffold_open_drawer, R.string.scaffold_close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                DrawerActivity.this.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                DrawerActivity.this.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                DrawerActivity.this.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                DrawerActivity.this.onDrawerStateChanged(newState);
            }
        };
    }

    private void addLeftStartDrawerFragment() {
        Fragment fragment = getLeftStartDrawerFragment();
        View view = findViewById(R.id.scaffold_left_start_drawer_container);
        if (view != null && fragment != null) {
            replaceFragment(R.id.scaffold_left_start_drawer_container, fragment, fragment.getClass().getName());
        } else if (view != null) {
            mDrawerLayout.removeView(view);
        }
    }

    private void addRightEndDrawerFragment() {
        Fragment fragment = getRightEndDrawerFragment();
        View view = findViewById(R.id.scaffold_right_end_drawer_container);
        if (view != null && fragment != null) {
            replaceFragment(R.id.scaffold_right_end_drawer_container, fragment, fragment.getClass().getName());
        } else if (view != null) {
            mDrawerLayout.removeView(view);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @CallSuper
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean drawerToggleHandled = false;
        if (mDrawerToggle != null) {
            drawerToggleHandled = mDrawerToggle.onOptionsItemSelected(item);
        }
        return drawerToggleHandled || super.onOptionsItemSelected(item);
    }

    public boolean isLeftStartDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public boolean isRightEndDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(GravityCompat.END);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        invalidateOptionsMenu();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (actionBar.getCustomView() == null) {
                actionBar.setDisplayShowTitleEnabled(false);
            }
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        invalidateOptionsMenu();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (actionBar.getCustomView() == null) {
                actionBar.setDisplayShowTitleEnabled(true);
            }
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected abstract Fragment getLeftStartDrawerFragment();

    protected abstract Fragment getRightEndDrawerFragment();
}
