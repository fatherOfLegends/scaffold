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

package com.lamcreations.scaffold.navigation.activities;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.view.ViewStub;

import com.lamcreations.scaffold.R;
import com.lamcreations.scaffold.common.views.CollapseMode;

public abstract class CollapsingToolbarTabDrawerActivity extends TabDrawerActivity {

    protected CollapsingToolbarLayout mCollapsingToolbarLayout;
    protected ViewStub mCollapsingToolbarLayoutBackdropViewStub;
    protected View mCollapsingToolbarLayoutBackdropView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupCollapsingToolbarLayout();
        setupCollapsingToolbarLayoutBackdrop();
    }

    @CallSuper
    protected void setupCollapsingToolbarLayout() {
        mCollapsingToolbarLayout = findViewById(R.id.scaffold_collapsing_toolbar_layout);
        assert mCollapsingToolbarLayout != null;
        ((AppBarLayout.LayoutParams) mCollapsingToolbarLayout.getLayoutParams()).setScrollFlags(getScrollFlags());
        mAppBarLayout.getLayoutParams().height = getCollapsingToolbarMaxHeight();
        ((CollapsingToolbarLayout.LayoutParams) mToolbar.getLayoutParams()).setCollapseMode(getActionbarCollapseMode());
    }

    @CallSuper
    protected void setupCollapsingToolbarLayoutBackdrop() {
        mCollapsingToolbarLayoutBackdropViewStub = findViewById(R.id.scaffold_collapsing_toolbar_backdrop_stub);
        mCollapsingToolbarLayoutBackdropViewStub.setLayoutResource(getCollapsingToolbarLayoutBackdropResId());
        mCollapsingToolbarLayoutBackdropView = mCollapsingToolbarLayoutBackdropViewStub.inflate();
        CollapsingToolbarLayout.LayoutParams layoutParams =
                ((CollapsingToolbarLayout.LayoutParams) mCollapsingToolbarLayoutBackdropView.getLayoutParams());
        layoutParams.setCollapseMode(getBackdropCollapseMode());
        layoutParams.setParallaxMultiplier(getParallaxMultiplier());
        mCollapsingToolbarLayoutBackdropView.setFitsSystemWindows(true);
    }

    @Override
    protected int getScrollFlags() {
        return AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
    }

    @CollapseMode
    protected int getBackdropCollapseMode() {
        return CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX;
    }

    @FloatRange(from = 0.0, to = 1.0)
    protected float getParallaxMultiplier() {
        return 0.0f;
    }

    @CollapseMode
    protected int getActionbarCollapseMode() {
        return CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN;
    }

    protected abstract int getCollapsingToolbarMaxHeight();

    @LayoutRes
    protected abstract int getCollapsingToolbarLayoutBackdropResId();

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.scaffold_activity_collapsing_toolbar_tab_drawer;
    }
}
