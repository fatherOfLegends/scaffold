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
