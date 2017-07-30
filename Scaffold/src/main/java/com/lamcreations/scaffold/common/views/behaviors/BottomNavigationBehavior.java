package com.lamcreations.scaffold.common.views.behaviors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

@SuppressWarnings("unused")
public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;
    private View mDependentSnackBar;

    public BottomNavigationBehavior() {
        super();
    }

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(final CoordinatorLayout parent, final BottomNavigationView child, final View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            mDependentSnackBar = dependency;
            updateSnackBarMargin(child);
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(final CoordinatorLayout parent, final BottomNavigationView child, final View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            mDependentSnackBar = dependency;
            updateSnackBarMargin(child);
            return true;
        }
        return false;
    }

    @Override
    public void onDependentViewRemoved(final CoordinatorLayout parent, final BottomNavigationView child, final View dependency) {
        if (mDependentSnackBar == dependency) {
            mDependentSnackBar = null;
        }
    }

    @Override
    public boolean onStartNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout, @NonNull final BottomNavigationView child,
                                       @NonNull final View directTargetChild, @NonNull final View target, final int nestedScrollAxes,
                                       @ViewCompat.NestedScrollType int type) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout, @NonNull final BottomNavigationView child,
                               @NonNull final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed, @ViewCompat.NestedScrollType int type) {
        if (dyConsumed > 0 && !this.mIsAnimatingOut) {
            animateOut(child);
        } else if (dyConsumed < 0) {
            animateIn(child);
        }
    }

    private void updateSnackBarMargin(final View bottomNavigationView) {
        if (mDependentSnackBar != null) {
            ((CoordinatorLayout.LayoutParams) mDependentSnackBar.getLayoutParams()).bottomMargin = (int) (bottomNavigationView.getHeight() - bottomNavigationView.getTranslationY());
            mDependentSnackBar.requestLayout();
        }
    }

    private void animateOut(final BottomNavigationView bottomNavigationView) {
        ViewCompat.animate(bottomNavigationView)
                .translationY(bottomNavigationView.getHeight())
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setUpdateListener(new ViewPropertyAnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(final View view) {
                        updateSnackBarMargin(view);
                    }
                })
                .setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(View view) {
                        BottomNavigationBehavior.this.mIsAnimatingOut = true;
                    }

                    public void onAnimationCancel(View view) {
                        BottomNavigationBehavior.this.mIsAnimatingOut = false;
                    }

                    public void onAnimationEnd(View view) {
                        BottomNavigationBehavior.this.mIsAnimatingOut = false;
                    }
                }).start();
    }

    private void animateIn(BottomNavigationView bottomNavigationView) {
        ViewCompat.animate(bottomNavigationView)
                .translationY(0)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setUpdateListener(new ViewPropertyAnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(final View view) {
                        updateSnackBarMargin(view);
                    }
                })
                .setListener(null)
                .start();
    }
}
