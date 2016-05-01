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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lamcreations.scaffold.common.config.Constants;


public abstract class BaseActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    protected Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void onBackStackChanged() {
    }

    protected Bundle getArguments() {
        Bundle args = new Bundle();
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.ARGS)) {
            args.putAll(intent.getBundleExtra(Constants.ARGS));
        }
        return args;
    }

    protected void addFragmentToStack(@IdRes int containerViewId, Fragment fragment) {
        addFragmentToStack(containerViewId, fragment, null);
    }

    protected void addFragmentToStack(@IdRes int containerViewId, Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void addFragmentToStackWithSharedElement(@IdRes int containerViewId, Fragment fragment,
                                                       View sharedView, String transitionName) {
        addFragmentToStackWithSharedElement(containerViewId, fragment, null, sharedView, transitionName);
    }

    protected void addFragmentToStackWithSharedElement(@IdRes int containerViewId, Fragment fragment,
                                                       String tag, View sharedView, String transitionName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, tag);
        fragmentTransaction.addSharedElement(sharedView, transitionName);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void removeFragment(@IdRes int containerViewId) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(containerViewId);
        if (fragment != null) {
            removeFragment(fragment);
        }
    }

    protected void removeFragment(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            removeFragment(fragment);
        }
    }

    protected void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    protected void replaceFragment(@IdRes int containerViewId, Fragment fragment) {
        replaceFragment(containerViewId, fragment, null);
    }

    protected void replaceFragment(@IdRes int containerViewId, Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, tag);
        fragmentTransaction.commit();
    }

    protected void popFragmentStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    protected void popFragmentStack(String tag, int flags) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(tag, flags);
    }
}
