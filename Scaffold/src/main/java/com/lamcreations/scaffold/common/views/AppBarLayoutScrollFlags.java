package com.lamcreations.scaffold.common.views;

import android.support.annotation.IntDef;
import android.support.design.widget.AppBarLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef(
        flag = true,
        value = {
                AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS,
                AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED,
                AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED,
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL,
                AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
        }
)
@Retention(RetentionPolicy.SOURCE)
public @interface AppBarLayoutScrollFlags {
}
