package com.lamcreations.scaffold.navigation;

import android.support.annotation.IntDef;
import android.support.design.widget.TabLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        TabLayout.MODE_FIXED,
        TabLayout.MODE_SCROLLABLE
})
@Retention(RetentionPolicy.SOURCE)
public @interface TabLayoutMode {
}
