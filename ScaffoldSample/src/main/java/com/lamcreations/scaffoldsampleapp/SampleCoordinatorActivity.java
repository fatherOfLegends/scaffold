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

package com.lamcreations.scaffoldsampleapp;

import com.lamcreations.scaffold.common.activities.CoordinatorActivity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;


public class SampleCoordinatorActivity extends CoordinatorActivity {


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
    protected int getActivityLayoutResId() {
        return R.layout.scaffold_activity_coordinator;
    }

    @Override
    protected void onUpNavigation() {
        ActivityCompat.finishAfterTransition(this);
    }
}
