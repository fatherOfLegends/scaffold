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
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.lamcreations.scaffold.R;


public abstract class SplashScreenActivity extends BaseActivity {

    protected ImageView mSplashImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scaffold_activity_splash_screen);

        mSplashImage = (ImageView) findViewById(R.id.scaffold_splash_image);
    }

    public void setSplashImage(@DrawableRes int resId) {
        mSplashImage.setImageResource(resId);
    }
}
