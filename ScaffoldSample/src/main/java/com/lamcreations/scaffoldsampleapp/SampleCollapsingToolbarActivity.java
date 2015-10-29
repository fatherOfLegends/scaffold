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

import com.bumptech.glide.Glide;
import com.lamcreations.scaffold.common.activities.CollapsingToolbarActivity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;


public class SampleCollapsingToolbarActivity extends CollapsingToolbarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(R.id.scaffold_content, new EarthquakesFragment());
    }

    @Override
    protected void setupCollapsingToolbarLayout() {
        super.setupCollapsingToolbarLayout();
        mCollapsingToolbarLayout.setTitle("North America");
        mCollapsingToolbarLayout.setContentScrimResource(R.color.primary);
    }

    @Override
    protected void setupCollapsingToolbarLayoutBackdrop() {
        super.setupCollapsingToolbarLayoutBackdrop();
        Glide.with(this).load(R.drawable.north_america).into((ImageView)mCollapsingToolbarLayoutBackdropView);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.frame_layout;
    }

    @Override
    protected int getCollapsingToolbarMaxHeight() {
        return getResources().getDimensionPixelSize(R.dimen.collapsing_toolbar_height);
    }

    @Override
    protected int getCollapsingToolbarLayoutBackdropResId() {
        return R.layout.image_view;
    }

    @Override
    protected float getParallaxMultiplier() {
        return 0.5f;
    }

    @Override
    protected int getFabAnchorId() {
        return R.id.scaffold_app_bar_layout;
    }

    @Override
    protected CoordinatorLayout.Behavior<FloatingActionButton> getFabBehavior() {
        return new FloatingActionButton.Behavior();
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
