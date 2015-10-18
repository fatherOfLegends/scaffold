package com.lamcreations.scaffoldsampleapp;


import com.lamcreations.scaffold.common.activities.SettingsActivity;


public class SampleSettingsActivity extends SettingsActivity {

    @Override
    protected int getPreferenceResId(String key) {
        return R.xml.settings;
    }
}
