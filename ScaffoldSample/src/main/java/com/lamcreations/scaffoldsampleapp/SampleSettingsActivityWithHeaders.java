package com.lamcreations.scaffoldsampleapp;


import com.lamcreations.scaffold.common.activities.SettingsActivity;


public class SampleSettingsActivityWithHeaders extends SettingsActivity {

    @Override
    protected int getPreferenceResId(String key) {
        if(key == null){
            return R.xml.settings_headers;
        }
        switch (key) {
            case "general":
                return R.xml.general;
            case "other":
                return R.xml.other;
        }
        return 0;
    }
}
