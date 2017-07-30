/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lamcreations.scaffold.common.views.behaviors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

@SuppressWarnings("unused")
public class FabBehavior extends FloatingActionButton.Behavior {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;
    private boolean mHideOnScroll = true;

    public FabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FabBehavior() {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout, @NonNull final FloatingActionButton child,
                                       @NonNull final View directTargetChild, @NonNull final View target, final int nestedScrollAxes,
                                       @ViewCompat.NestedScrollType int type) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout, @NonNull final FloatingActionButton child,
                               @NonNull final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed, @ViewCompat.NestedScrollType int type) {
        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getScaleX() > 0f) {
            animateOut(child);
        } else if (dyConsumed < 0 && child.getScaleX() == 0f) {
            animateIn(child);
        }
    }

    private void animateOut(final FloatingActionButton button) {
        if (mHideOnScroll) {
            ViewCompat.animate(button)
                    .scaleX(0.0F)
                    .scaleY(0.0F)
                    .alpha(0.0F)
                    .setInterpolator(INTERPOLATOR)
                    .withLayer()
                    .setListener(new ViewPropertyAnimatorListener() {
                        public void onAnimationStart(View view) {
                            FabBehavior.this.mIsAnimatingOut = true;
                        }

                        public void onAnimationCancel(View view) {
                            FabBehavior.this.mIsAnimatingOut = false;
                        }

                        public void onAnimationEnd(View view) {
                            FabBehavior.this.mIsAnimatingOut = false;
                        }
                    }).start();
        }
    }

    private void animateIn(FloatingActionButton button) {
        if (mHideOnScroll) {
            button.setVisibility(View.VISIBLE);
            ViewCompat.animate(button)
                    .scaleX(1.0F)
                    .scaleY(1.0F)
                    .alpha(1.0F)
                    .setInterpolator(INTERPOLATOR)
                    .withLayer()
                    .setListener(null)
                    .start();
        }
    }

    public boolean isHideOnScroll() {
        return mHideOnScroll;
    }

    public void setHideOnScroll(final boolean hideOnScroll) {
        mHideOnScroll = hideOnScroll;
    }
}
