package com.lamcreations.scaffold.common.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.lamcreations.scaffold.R;

public class ScaffoldThemeUtils {

    private static final int[] SCAFFOLD_CHECK_ATTRS = {
            R.attr.scaffoldTheme
    };

    public static void checkScaffoldTheme(Context context) {
        TypedArray a = context.obtainStyledAttributes(SCAFFOLD_CHECK_ATTRS);
        boolean failed = true;
        if (a != null) {
            failed = !a.hasValue(0);
            a.recycle();
        }
        if (failed) {
            throw new IllegalArgumentException("You need to use a Theme.Scaffold.Base theme "
                    + "(or descendant) with the Scaffold library.");
        }
    }
}
