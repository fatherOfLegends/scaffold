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

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.lamcreations.scaffold.R;


public abstract class ToolbarActivity extends BaseActivity {

    protected CharSequence mActionBarTitle;
    protected Toolbar mToolbar;
    protected View mContentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActionBar();
        mContentView = findViewById(R.id.scaffold_content);
    }

    protected CharSequence getActionBarTitle() {
        if (mActionBarTitle == null) {
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
        if (item.getItemId() == android.R.id.home || item.getItemId() == R.id.home) {
            onUpNavigation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.scaffold_activity_toolbar;
    }

    protected abstract void onUpNavigation();
}
