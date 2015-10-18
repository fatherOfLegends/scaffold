package com.lamcreations.scaffold.common.activities;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.view.ViewStub;

import com.lamcreations.scaffold.R;


public abstract class CollapsingToolbarActivity extends CoordinatorActivity {

    @IntDef({CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_OFF,
             CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX,
             CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
    })
    public @interface CollapseMode {}

    protected CollapsingToolbarLayout mCollapsingToolbarLayout;
    protected ViewStub mCollapsingToolbarLayoutBackdropViewStub;
    protected View mCollapsingToolbarLayoutBackdropView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupCollapsingToolbarLayout();
        setupCollapsingToolbarLayoutBackdrop();
    }

    @CallSuper
    protected void setupCollapsingToolbarLayout() {
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout);
        ((AppBarLayout.LayoutParams)mCollapsingToolbarLayout.getLayoutParams()).setScrollFlags(getScrollFlags());
        mAppBarLayout.getLayoutParams().height = getCollapsingToolbarMaxHeight();
        ((CollapsingToolbarLayout.LayoutParams)mToolbar.getLayoutParams()).setCollapseMode(getActionbarCollapseMode());
    }

    @CallSuper
    protected void setupCollapsingToolbarLayoutBackdrop() {
        mCollapsingToolbarLayoutBackdropViewStub = (ViewStub)findViewById(R.id.collapsing_toolbar_backdrop_stub);
        mCollapsingToolbarLayoutBackdropViewStub.setLayoutResource(getCollapsingToolbarLayoutBackdropResId());
        mCollapsingToolbarLayoutBackdropView = mCollapsingToolbarLayoutBackdropViewStub.inflate();
        CollapsingToolbarLayout.LayoutParams layoutParams =
                ((CollapsingToolbarLayout.LayoutParams)mCollapsingToolbarLayoutBackdropView.getLayoutParams());
        layoutParams.setCollapseMode(getBackdropCollapseMode());
        layoutParams.setParallaxMultiplier(getParallaxMultiplier());
        mCollapsingToolbarLayoutBackdropView.setLayoutParams(layoutParams);
        mCollapsingToolbarLayoutBackdropView.setFitsSystemWindows(true);
    }

    @Override
    protected int getScrollFlags() {
        return AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
    }

    @CollapseMode
    protected int getBackdropCollapseMode() {
        return CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX;
    }

    @FloatRange(from = 0.0, to = 1.0)
    protected float getParallaxMultiplier(){
        return 0.0f;
    }

    @CollapseMode
    protected  int getActionbarCollapseMode(){
        return CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN;
    }

    protected abstract int getCollapsingToolbarMaxHeight();

    @LayoutRes
    protected abstract int getCollapsingToolbarLayoutBackdropResId();

    @Override
    protected int getActivityLayoutResId() {
        return R.layout.activity_collapsing_toolbar;
    }
}
