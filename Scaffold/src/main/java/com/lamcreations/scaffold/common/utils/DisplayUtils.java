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

package com.lamcreations.scaffold.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DisplayUtils {

    public static int screenPixels(Context context, int densityPixels) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (densityPixels * displayMetrics.density);
    }

    public static void changeFonts(ViewGroup root, Typeface typeFace) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(typeFace);
            } else if (v instanceof ViewGroup) {
                changeFonts((ViewGroup) v, typeFace);
            }
        }
    }
}
