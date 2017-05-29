package com.lamcreations.scaffold.common.views;

import android.support.annotation.IntDef;
import android.support.design.widget.CollapsingToolbarLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_OFF,
        CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX,
        CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
})
@Retention(RetentionPolicy.SOURCE)
public @interface CollapseMode {
}