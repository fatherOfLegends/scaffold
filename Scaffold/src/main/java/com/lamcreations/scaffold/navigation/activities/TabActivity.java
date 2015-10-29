package com.lamcreations.scaffold.navigation.activities;
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

import com.lamcreations.scaffold.R;
import com.lamcreations.scaffold.common.activities.CoordinatorActivity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IntDef;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public abstract class TabActivity extends CoordinatorActivity {

    @IntDef({TabLayout.MODE_FIXED, TabLayout.MODE_SCROLLABLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TabLayoutMode {}

    @IntDef({TabLayout.GRAVITY_CENTER, TabLayout.GRAVITY_FILL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TabLayoutGravity {}

    public static final String CURRENT_TAB_POSITION = "currentTabPosition";
    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;
    protected int mCurrentTabPosition = 0;

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.scaffold_activity_tab;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mCurrentTabPosition = savedInstanceState.getInt(CURRENT_TAB_POSITION, getInitialTabPosition());
        } else {
            mCurrentTabPosition = getInitialTabPosition();
        }
        setupTabs();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_TAB_POSITION, mCurrentTabPosition);
    }

    @CallSuper
    protected void setupTabs(){
        PagerAdapter pagerAdapter = getPagerAdapter();
        mTabLayout = (TabLayout) findViewById(R.id.scaffold_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.scaffold_content);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setCurrentItem(mCurrentTabPosition);
        mTabLayout.setTabMode(getTabMode());
        mTabLayout.setTabGravity(getTabGravity());
        mTabLayout.setupWithViewPager(mViewPager);
        TabLayout.Tab tab = mTabLayout.getTabAt(mCurrentTabPosition);
        if(tab != null){
            tab.select();
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentTabPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.scaffold_tab_view_pager;
    }

    @TabLayoutMode
    protected int getTabMode(){
        return TabLayout.MODE_FIXED;
    }

    @TabLayoutGravity
    protected int getTabGravity(){
        return TabLayout.GRAVITY_FILL;
    }

    protected abstract PagerAdapter getPagerAdapter();

    protected abstract int getInitialTabPosition();
}
