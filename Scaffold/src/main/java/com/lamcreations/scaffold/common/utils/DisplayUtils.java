package com.lamcreations.scaffold.common.utils;


import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DisplayUtils {

    public static int screenPixels(Context context, int densityPixels){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)(densityPixels * displayMetrics.density);
    }

    public static void changeFonts(ViewGroup root, Typeface typeFace) {
        for(int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if(v instanceof TextView) {
                ((TextView)v).setTypeface(typeFace);
            } else if(v instanceof ViewGroup) {
                changeFonts((ViewGroup)v, typeFace);
            }
        }
    }
}
