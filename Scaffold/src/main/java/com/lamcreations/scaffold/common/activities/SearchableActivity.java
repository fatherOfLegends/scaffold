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

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.lamcreations.scaffold.R;

import java.util.List;


public abstract class SearchableActivity extends CoordinatorActivity implements TextWatcher {

    private static final int SPEECH_REQUEST_CODE = 10;

    protected EditText mSearchEditText;
    protected String mPreviousQuery = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupSearchEditText();
    }

    @CallSuper
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String query = results.get(0);
            mSearchEditText.setText(query);
            performSearch(query);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.scaffold_activity_searchable;
    }

    protected boolean handleNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            mSearchEditText.setText(query);
            performSearch(query);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (showClearSearch()) {
            getMenuInflater().inflate(R.menu.scaffold_clear_search, menu);
        }
        if (showVoiceSearch()) {
            getMenuInflater().inflate(R.menu.scaffold_voice_search, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem clearSearch = menu.findItem(R.id.clear_search);
        if (clearSearch != null) {
            clearSearch.setVisible(!getQuery().isEmpty());
        }
        MenuItem voiceSearch = menu.findItem(R.id.voice_search);
        if (voiceSearch != null) {
            voiceSearch.setVisible(getQuery().isEmpty());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear_search) {
            clearSearchQuery();
            return true;
        } else if (id == R.id.voice_search) {
            beginVoiceSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            mAppBarLayout = (AppBarLayout) findViewById(R.id.scaffold_app_bar_layout);
            mToolbar = (Toolbar) findViewById(R.id.scaffold_toolbar);
            assert mToolbar != null;
            if (mToolbar.getParent().getClass().equals(AppBarLayout.class)) {
                ((AppBarLayout.LayoutParams) mToolbar.getLayoutParams()).setScrollFlags(getScrollFlags());
            }
            setSupportActionBar(mToolbar);
            actionBar = getSupportActionBar();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @CallSuper
    protected void setupSearchEditText() {
        mSearchEditText = (EditText) findViewById(R.id.scaffold_search_edit_text);
        assert mSearchEditText != null;
        mSearchEditText.addTextChangedListener(this);
    }

    @NonNull
    protected String getQuery() {
        return mSearchEditText.getText().toString();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @CallSuper
    @Override
    public void afterTextChanged(Editable s) {
        if (mPreviousQuery.isEmpty()) {
            invalidateOptionsMenu();
        } else if (s.length() == 0) {
            invalidateOptionsMenu();
        }

        if (performSearchOnQueryChange()) {
            performSearch(getQuery());
        }

        mPreviousQuery = getQuery();
    }

    protected void clearSearchQuery() {
        mSearchEditText.setText(null);
    }

    protected void beginVoiceSearch() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    protected abstract boolean performSearchOnQueryChange();

    protected abstract boolean showVoiceSearch();

    protected abstract boolean showClearSearch();

    protected abstract void performSearch(String query);
}
