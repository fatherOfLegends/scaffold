package com.lamcreations.scaffold.common.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat {

    public static final String PREFERENCE_RES_ID = "PreferenceResId";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(getArguments().getInt(PREFERENCE_RES_ID));
    }
}
