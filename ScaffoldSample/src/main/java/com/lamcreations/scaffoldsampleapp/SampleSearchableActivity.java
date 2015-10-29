package com.lamcreations.scaffoldsampleapp;
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
