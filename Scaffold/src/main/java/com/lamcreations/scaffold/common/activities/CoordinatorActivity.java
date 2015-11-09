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
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.lamcreations.scaffold.R;
import com.lamcreations.scaffold.common.views.behaviors.FabBehavior;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public abstract class CoordinatorActivity extends ToolbarActivity {

    @IntDef(
            flag = true,
            value = {
                    AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS,
                    AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED,
                    AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED,
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL,
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
            }
    )
    @Retention(RetentionPolicy.SOURCE)
    public @interface AppBarLayoutScrollFlags {
    }

    protected ViewStub mContentViewStub;
    protected View mContentView;
    protected AppBarLayout mAppBarLayout;

    protected CoordinatorLayout mCoordinatorLayout;
    protected FloatingActionButton mFloatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentViewStub = (ViewStub) findViewById(R.id.scaffold_content_stub);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.scaffold_coordinator_layout);
        setContent(getContentLayoutResId());
        initFab();
    }

    protected void setContent(@LayoutRes int resId) {
        if (findViewById(R.id.scaffold_content) == null && mContentViewStub != null) {
            mContentViewStub.setLayoutResource(resId);
            mContentView = mContentViewStub.inflate();
        }
    }

    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            mAppBarLayout = (AppBarLayout) findViewById(R.id.scaffold_app_bar_layout);
            mToolbar = (Toolbar) findViewById(R.id.scaffold_toolbar);
            if (mToolbar.getParent().getClass().equals(AppBarLayout.class)) {
                ((AppBarLayout.LayoutParams) mToolbar.getLayoutParams()).setScrollFlags(getScrollFlags());
            }
            setSupportActionBar(mToolbar);
            actionBar = getSupportActionBar();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getActionBarTitle());
    }

    @AppBarLayoutScrollFlags
    protected int getScrollFlags() {
        return AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS |
                AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP;
    }

    private void initFab() {
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.scaffold_floating_action_button);
        if (mFloatingActionButton != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mFloatingActionButton.getLayoutParams();
            layoutParams.setAnchorId(getFabAnchorId());
            layoutParams.anchorGravity = getFabAnchorGravity();
            layoutParams.setBehavior(getFabBehavior());
            boolean show = setupFab();
            if (!show) {
                ViewGroup parent = (ViewGroup) mFloatingActionButton.getParent();
                parent.removeView(mFloatingActionButton);
            }
        }
    }

    @IdRes
    protected int getFabAnchorId() {
        return R.id.scaffold_content;
    }

    protected int getFabAnchorGravity() {
        return Gravity.BOTTOM | GravityCompat.END;
    }

    protected CoordinatorLayout.Behavior<FloatingActionButton> getFabBehavior() {
        return new FabBehavior();
    }

    @LayoutRes
    protected abstract int getContentLayoutResId();

    protected abstract boolean setupFab();
}
