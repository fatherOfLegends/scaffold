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

package com.lamcreations.scaffold.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.lamcreations.scaffold.R;
import com.lamcreations.scaffold.common.utils.CollapsingTextHelper;

import static android.support.v4.content.ContextCompat.getColor;

public class CollapsingToolbarTabLayout extends CollapsingToolbarLayout {

    private View mDummyView;
    private CollapsingTextHelper mCollapsingTextHelper;
    private OffsetUpdateListener mOnOffsetChangedListener;
    private Rect mTmpRect;

    private int mExpandedMarginLeft;
    private int mExpandedMarginTop;
    private int mExpandedMarginRight;
    private int mExpandedMarginBottom;

    private int mActionBarHeight = 0;

    public CollapsingToolbarTabLayout(Context context) {
        this(context, null);
    }

    public CollapsingToolbarTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsingToolbarTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setCollapsedTitleTextColor(getColor(getContext(), android.R.color.transparent));
        super.setExpandedTitleColor(getColor(getContext(), android.R.color.transparent));
        TypedValue tv = new TypedValue();
        if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            mActionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        mTmpRect = new Rect();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CollapsingToolbarTabLayout, defStyleAttr,
                R.style.Widget_Scaffold_CollapsingToolbarTabLayout);
        int tp;
        boolean isRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        mExpandedMarginLeft = mExpandedMarginTop = mExpandedMarginRight = mExpandedMarginBottom = a
                .getDimensionPixelSize(R.styleable.CollapsingToolbarTabLayout_scaffoldExpandedTitleMargin, 0);

        if (a.hasValue(R.styleable.CollapsingToolbarTabLayout_scaffoldExpandedTitleMarginStart)) {
            tp = a.getDimensionPixelSize(R.styleable.CollapsingToolbarTabLayout_scaffoldExpandedTitleMarginStart, 0);
            if (isRtl) {
                mExpandedMarginRight = tp;
            } else {
                mExpandedMarginLeft = tp;
            }
        }

        if (a.hasValue(R.styleable.CollapsingToolbarTabLayout_scaffoldExpandedTitleMarginEnd)) {
            tp = a.getDimensionPixelSize(R.styleable.CollapsingToolbarTabLayout_scaffoldExpandedTitleMarginEnd, 0);
            if (isRtl) {
                mExpandedMarginLeft = tp;
            } else {
                mExpandedMarginRight = tp;
            }
        }

        if (a.hasValue(R.styleable.CollapsingToolbarTabLayout_scaffoldExpandedTitleMarginTop)) {
            mExpandedMarginTop = a.getDimensionPixelSize(R.styleable.CollapsingToolbarTabLayout_scaffoldExpandedTitleMarginTop, 0);
        }

        if (a.hasValue(R.styleable.CollapsingToolbarTabLayout_scaffoldExpandedTitleMarginBottom)) {
            mExpandedMarginBottom = a.getDimensionPixelSize(R.styleable.CollapsingToolbarTabLayout_scaffoldExpandedTitleMarginBottom, 0);
        }

        tp = a.getResourceId(R.styleable.CollapsingToolbarTabLayout_scaffoldExpandedTitleTextAppearance,
                R.style.TextAppearance_AppCompat_Title);
        getCollapsingTextHelper().setExpandedTextAppearance(tp);
        tp = a.getResourceId(R.styleable.CollapsingToolbarTabLayout_scaffoldCollapsedTitleTextAppearance,
                R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
        getCollapsingTextHelper().setCollapsedTextAppearance(tp);
        String title = a.getString(R.styleable.CollapsingToolbarTabLayout_scaffoldTitle);
        setTitle(title);
        a.recycle();
    }

    private CollapsingTextHelper getCollapsingTextHelper() {
        if (mCollapsingTextHelper == null) {
            mCollapsingTextHelper = new CollapsingTextHelper(this);
            mCollapsingTextHelper.setCollapsedTextVerticalGravity(Gravity.CENTER_VERTICAL);
            mCollapsingTextHelper.setExpandedTextVerticalGravity(Gravity.BOTTOM);
            mCollapsingTextHelper.setTextSizeInterpolator(CollapsingTextHelper.AnimationUtils.DECELERATE_INTERPOLATOR);
        }
        return mCollapsingTextHelper;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = this.getParent();
        if (parent instanceof AppBarLayout) {
            if (mOnOffsetChangedListener == null) {
                mOnOffsetChangedListener = new CollapsingToolbarTabLayout.OffsetUpdateListener();
            }

            ((AppBarLayout) parent).addOnOffsetChangedListener(mOnOffsetChangedListener);
        }
    }

    protected void onDetachedFromWindow() {
        ViewParent parent = this.getParent();
        if (mOnOffsetChangedListener != null && parent instanceof AppBarLayout) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(mOnOffsetChangedListener);
        }

        super.onDetachedFromWindow();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (getDummyView() != null) {
            ViewGroupUtils.getDescendantRect(this, mDummyView, mTmpRect);
            getCollapsingTextHelper().setCollapsedBounds(mTmpRect.left, bottom - mTmpRect.height(), mTmpRect.right, bottom - mTmpRect.height() +
                    mActionBarHeight);
            getCollapsingTextHelper().setExpandedBounds(left + mExpandedMarginLeft, mTmpRect.bottom + mExpandedMarginTop, right -
                            mExpandedMarginRight,
                    bottom - mExpandedMarginBottom);
            getCollapsingTextHelper().recalculate();
        }
    }

    private View getDummyView() {
        if (mDummyView == null) {
            Toolbar toolBar = (Toolbar) findViewById(R.id.scaffold_toolbar);
            int size = toolBar.getChildCount();
            for (int i = 0; i < size; ++i) {
                View view = toolBar.getChildAt(i);
                if (!view.isFocusable() && !view.isClickable()) {
                    mDummyView = view;
                }
            }
        }
        return mDummyView;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        getCollapsingTextHelper().draw(canvas);
    }

    @Override
    public void setTitle(CharSequence title) {
        getCollapsingTextHelper().setText(title);
    }

    public void setCollapsedTitleTextAppearance(int resId) {
        getCollapsingTextHelper().setCollapsedTextAppearance(resId);
    }

    public void setCollapsedTitleTextColor(int color) {
        getCollapsingTextHelper().setCollapsedTextColor(color);
    }

    public void setExpandedTitleTextAppearance(int resId) {
        getCollapsingTextHelper().setExpandedTextAppearance(resId);
    }

    public void setExpandedTitleColor(int color) {
        getCollapsingTextHelper().setExpandedTextColor(color);
    }

    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {

        private OffsetUpdateListener() {
        }

        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            int insetTop = getChildAt(0) != null ? getChildAt(0).getTop() : 0;
            int expandRange = CollapsingToolbarTabLayout.this.getHeight() - ViewCompat.getMinimumHeight(CollapsingToolbarTabLayout.this) - insetTop;
            getCollapsingTextHelper().setExpansionFraction((float) Math.abs(verticalOffset) / (float) expandRange);
        }
    }


    static class ViewGroupUtils {

        private static final ViewGroupUtils.ViewGroupUtilsImpl IMPL;

        ViewGroupUtils() {
        }

        static void offsetDescendantRect(ViewGroup parent, View descendant, Rect rect) {
            IMPL.offsetDescendantRect(parent, descendant, rect);
        }

        static void getDescendantRect(ViewGroup parent, View descendant, Rect out) {
            out.set(0, 0, descendant.getWidth(), descendant.getHeight());
            offsetDescendantRect(parent, descendant, out);
        }

        static {
            int version = Build.VERSION.SDK_INT;
            if (version >= 11) {
                IMPL = new ViewGroupUtils.ViewGroupUtilsImplHoneycomb();
            } else {
                IMPL = new ViewGroupUtils.ViewGroupUtilsImplBase();
            }
        }


        private static class ViewGroupUtilsImplHoneycomb implements ViewGroupUtils.ViewGroupUtilsImpl {

            private ViewGroupUtilsImplHoneycomb() {
            }

            public void offsetDescendantRect(ViewGroup parent, View child, Rect rect) {
                ViewGroupUtilsHoneycomb.offsetDescendantRect(parent, child, rect);
            }
        }


        private static class ViewGroupUtilsImplBase implements ViewGroupUtils.ViewGroupUtilsImpl {

            private ViewGroupUtilsImplBase() {
            }

            public void offsetDescendantRect(ViewGroup parent, View child, Rect rect) {
                parent.offsetDescendantRectToMyCoords(child, rect);
            }
        }


        private interface ViewGroupUtilsImpl {

            void offsetDescendantRect(ViewGroup var1, View var2, Rect var3);
        }
    }


    static class ViewGroupUtilsHoneycomb {

        private static final ThreadLocal<Matrix> sMatrix = new ThreadLocal<>();
        private static final ThreadLocal<RectF> sRectF = new ThreadLocal<>();
        private static final Matrix IDENTITY = new Matrix();

        ViewGroupUtilsHoneycomb() {
        }

        public static void offsetDescendantRect(ViewGroup group, View child, Rect rect) {
            Matrix m = sMatrix.get();
            if (m == null) {
                m = new Matrix();
                sMatrix.set(m);
            } else {
                m.set(IDENTITY);
            }

            offsetDescendantMatrix(group, child, m);
            RectF rectF = sRectF.get();
            if (rectF == null) {
                rectF = new RectF();
            }

            rectF.set(rect);
            m.mapRect(rectF);
            rect.set((int) (rectF.left + 0.5F), (int) (rectF.top + 0.5F), (int) (rectF.right + 0.5F), (int) (rectF.bottom + 0.5F));
        }

        static void offsetDescendantMatrix(ViewParent target, View view, Matrix m) {
            ViewParent parent = view.getParent();
            if (parent instanceof View && parent != target) {
                View vp = (View) parent;
                offsetDescendantMatrix(target, vp, m);
                m.preTranslate((float) (-vp.getScrollX()), (float) (-vp.getScrollY()));
            }

            m.preTranslate((float) view.getLeft(), (float) view.getTop());
            if (!view.getMatrix().isIdentity()) {
                m.preConcat(view.getMatrix());
            }
        }
    }
}
