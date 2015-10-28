package com.lamcreations.scaffold.common.activities;

import android.os.Bundle;
import android.support.annotation.XmlRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.lamcreations.scaffold.R;
import com.lamcreations.scaffold.common.fragments.SettingsFragment;


public abstract class SettingsActivity extends ToolbarActivity
        implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback,
        PreferenceFragmentCompat.OnPreferenceStartScreenCallback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(R.id.scaffold_content, getSettingsFragment());
    }

    private Fragment getSettingsFragment() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putInt(SettingsFragment.PREFERENCE_RES_ID, getPreferenceResId(null));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.scaffold_activity_settings;
    }

    protected boolean isDualPane() {
        return findViewById(R.id.scaffold_content_end_pane) != null;
    }

    @XmlRes
    protected abstract int getPreferenceResId(String key);

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat callingFragment, Preference preference) {
        try {
            Fragment fragment = (Fragment) Class.forName(preference.getFragment()).newInstance();
            Bundle args = new Bundle();
            args.putInt(SettingsFragment.PREFERENCE_RES_ID, getPreferenceResId(preference.getKey()));
            fragment.setArguments(args);
            if(isDualPane()){
                replaceFragment(R.id.scaffold_content_end_pane, fragment);
            } else {
                addFragmentToStack(R.id.scaffold_content, fragment);
            }
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragmentCompat callingFragment, PreferenceScreen preferenceScreen) {
        try {
            Fragment fragment = (Fragment) Class.forName(preferenceScreen.getFragment()).newInstance();
            Bundle args = new Bundle();
            args.putInt(SettingsFragment.PREFERENCE_RES_ID, getPreferenceResId(preferenceScreen.getKey()));
            fragment.setArguments(args);
            addFragmentToStack(R.id.scaffold_content, fragment);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    protected void onUpNavigation() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            popFragmentStack();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }
}