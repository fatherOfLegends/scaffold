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

package com.lamcreations.scaffold.common.utils;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.lamcreations.scaffold.R;


@SuppressWarnings("unused")
public class CollapsingTextHelper {

    private static final boolean USE_SCALING_TEXTURE;
    private final View mView;
    private float mExpandedFraction;
    private final Rect mExpandedBounds;
    private final Rect mCollapsedBounds;
    private int mExpandedTextVerticalGravity = 16;
    private int mCollapsedTextVerticalGravity = 16;
    private float mExpandedTextSize;
    private float mCollapsedTextSize;
    private int mExpandedTextColor;
    private int mCollapsedTextColor;
    private float mExpandedTop;
    private float mCollapsedTop;
    private CharSequence mText;
    private CharSequence mTextToDraw;
    private float mTextWidth;
    private boolean mIsRtl;
    private boolean mUseTexture;
    private Bitmap mExpandedTitleTexture;
    private Paint mTexturePaint;
    private float mTextureAscent;
    private float mCurrentLeft;
    private float mCurrentRight;
    private float mCurrentTop;
    private float mScale;
    private float mCurrentTextSize;
    private final TextPaint mTextPaint;
    private Interpolator mPositionInterpolator;
    private Interpolator mTextSizeInterpolator;

    public CollapsingTextHelper(View view) {
        this.mView = view;
        this.mTextPaint = new TextPaint();
        this.mTextPaint.setAntiAlias(true);
        this.mCollapsedBounds = new Rect();
        this.mExpandedBounds = new Rect();
    }

    public void setTextSizeInterpolator(Interpolator interpolator) {
        this.mTextSizeInterpolator = interpolator;
        this.recalculate();
    }

    public void setPositionInterpolator(Interpolator interpolator) {
        this.mPositionInterpolator = interpolator;
        this.recalculate();
    }

    public void setExpandedTextSize(float textSize) {
        if (this.mExpandedTextSize != textSize) {
            this.mExpandedTextSize = textSize;
            this.recalculate();
        }

    }

    public void setCollapsedTextSize(float textSize) {
        if (this.mCollapsedTextSize != textSize) {
            this.mCollapsedTextSize = textSize;
            this.recalculate();
        }

    }

    public void setCollapsedTextColor(int textColor) {
        if (this.mCollapsedTextColor != textColor) {
            this.mCollapsedTextColor = textColor;
            this.recalculate();
        }

    }

    public void setExpandedTextColor(int textColor) {
        if (this.mExpandedTextColor != textColor) {
            this.mExpandedTextColor = textColor;
            this.recalculate();
        }

    }

    public void setExpandedBounds(int left, int top, int right, int bottom) {
        this.mExpandedBounds.set(left, top, right, bottom);
    }

    public void setCollapsedBounds(int left, int top, int right, int bottom) {
        this.mCollapsedBounds.set(left, top, right, bottom);
    }

    public void setExpandedTextVerticalGravity(int gravity) {
        gravity &= 112;
        if (this.mExpandedTextVerticalGravity != gravity) {
            this.mExpandedTextVerticalGravity = gravity;
            this.recalculate();
        }

    }

    public void setCollapsedTextVerticalGravity(int gravity) {
        gravity &= 112;
        if (this.mCollapsedTextVerticalGravity != gravity) {
            this.mCollapsedTextVerticalGravity = gravity;
            this.recalculate();
        }

    }

    @SuppressLint("PrivateResource")
    public void setCollapsedTextAppearance(int resId) {
        TypedArray a = this.mView.getContext().obtainStyledAttributes(resId, R.styleable.TextAppearance);
        if (a.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.mCollapsedTextColor = a.getColor(R.styleable.TextAppearance_android_textColor, 0);
        }

        if (a.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.mCollapsedTextSize = (float) a.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        }

        a.recycle();
        this.recalculate();
    }

    @SuppressLint("PrivateResource")
    public void setExpandedTextAppearance(int resId) {
        TypedArray a = this.mView.getContext().obtainStyledAttributes(resId, R.styleable.TextAppearance);
        if (a.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.mExpandedTextColor = a.getColor(R.styleable.TextAppearance_android_textColor, 0);
        }

        if (a.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.mExpandedTextSize = (float) a.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        }

        a.recycle();
        this.recalculate();
    }

    public void setExpansionFraction(float fraction) {
        fraction = MathUtils.constrain(fraction, 0.0F, 1.0F);
        if (fraction != this.mExpandedFraction) {
            this.mExpandedFraction = fraction;
            this.calculateOffsets();
        }
    }

    public float getExpansionFraction() {
        return this.mExpandedFraction;
    }

    public float getCollapsedTextSize() {
        return this.mCollapsedTextSize;
    }

    public float getExpandedTextSize() {
        return this.mExpandedTextSize;
    }

    private void calculateOffsets() {
        float fraction = this.mExpandedFraction;
        this.mCurrentLeft = interpolate((float) this.mExpandedBounds.left, (float) this.mCollapsedBounds.left, fraction, this.mPositionInterpolator);
        this.mCurrentTop = interpolate(this.mExpandedTop, this.mCollapsedTop, fraction, this.mPositionInterpolator);
        this.mCurrentRight = interpolate((float) this.mExpandedBounds.right, (float) this.mCollapsedBounds.right, fraction, this.mPositionInterpolator);
        this.setInterpolatedTextSize(interpolate(this.mExpandedTextSize, this.mCollapsedTextSize, fraction, this.mTextSizeInterpolator));
        if (this.mCollapsedTextColor != this.mExpandedTextColor) {
            this.mTextPaint.setColor(blendColors(this.mExpandedTextColor, this.mCollapsedTextColor, fraction));
        } else {
            this.mTextPaint.setColor(this.mCollapsedTextColor);
        }

        ViewCompat.postInvalidateOnAnimation(this.mView);
    }

    private void calculateBaselines() {
        this.mTextPaint.setTextSize(this.mCollapsedTextSize);
        float textHeight;
        float textOffset;
        switch (this.mCollapsedTextVerticalGravity) {
            case 16:
            default:
                textHeight = this.mTextPaint.descent() - this.mTextPaint.ascent();
                textOffset = textHeight / 2.0F - this.mTextPaint.descent();
                this.mCollapsedTop = (float) this.mCollapsedBounds.centerY() + textOffset;
                break;
            case 48:
                this.mCollapsedTop = (float) this.mCollapsedBounds.top - this.mTextPaint.ascent();
                break;
            case 80:
                this.mCollapsedTop = (float) this.mCollapsedBounds.bottom;
        }

        this.mTextPaint.setTextSize(this.mExpandedTextSize);
        switch (this.mExpandedTextVerticalGravity) {
            case 16:
            default:
                textHeight = this.mTextPaint.descent() - this.mTextPaint.ascent();
                textOffset = textHeight / 2.0F - this.mTextPaint.descent();
                this.mExpandedTop = (float) this.mExpandedBounds.centerY() + textOffset;
                break;
            case 48:
                this.mExpandedTop = (float) this.mExpandedBounds.top - this.mTextPaint.ascent();
                break;
            case 80:
                this.mExpandedTop = (float) this.mExpandedBounds.bottom;
        }

        this.mTextureAscent = this.mTextPaint.ascent();
        final float textureDescent = this.mTextPaint.descent();
        this.clearTexture();
    }

    public void draw(Canvas canvas) {
        int saveCount = canvas.save();
        if (this.mTextToDraw != null) {
            boolean isRtl = this.mIsRtl;
            float x = isRtl ? this.mCurrentRight : this.mCurrentLeft;
            float y = this.mCurrentTop;
            boolean drawTexture = this.mUseTexture && this.mExpandedTitleTexture != null;
            this.mTextPaint.setTextSize(this.mCurrentTextSize);
            float ascent;
            if (drawTexture) {
                ascent = this.mTextureAscent * this.mScale;
            } else {
                ascent = this.mTextPaint.ascent() * this.mScale;
            }

            if (drawTexture) {
                y += ascent;
            }

            if (this.mScale != 1.0F) {
                canvas.scale(this.mScale, this.mScale, x, y);
            }

            if (isRtl) {
                x -= this.mTextWidth;
            }

            if (drawTexture) {
                canvas.drawBitmap(this.mExpandedTitleTexture, x, y, this.mTexturePaint);
            } else {
                canvas.drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), x, y, this.mTextPaint);
            }
        }

        canvas.restoreToCount(saveCount);
    }

    private boolean calculateIsRtl(CharSequence text) {
        boolean defaultIsRtl = ViewCompat.getLayoutDirection(this.mView) == ViewCompat.LAYOUT_DIRECTION_RTL;
        return (defaultIsRtl ? TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL : TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR).isRtl(text, 0, text.length());
    }

    private void setInterpolatedTextSize(float textSize) {
        if (this.mText != null) {
            boolean updateDrawText = false;
            float availableWidth;
            float newTextSize;
            if (isClose(textSize, this.mCollapsedTextSize)) {
                availableWidth = (float) this.mCollapsedBounds.width();
                newTextSize = this.mCollapsedTextSize;
                this.mScale = 1.0F;
            } else {
                availableWidth = (float) this.mExpandedBounds.width();
                newTextSize = this.mExpandedTextSize;
                if (isClose(textSize, this.mExpandedTextSize)) {
                    this.mScale = 1.0F;
                } else {
                    this.mScale = textSize / this.mExpandedTextSize;
                }
            }

            if (availableWidth > 0.0F) {
                updateDrawText = this.mCurrentTextSize != newTextSize;
                this.mCurrentTextSize = newTextSize;
            }

            if (this.mTextToDraw == null || updateDrawText) {
                this.mTextPaint.setTextSize(this.mCurrentTextSize);
                CharSequence title = TextUtils.ellipsize(this.mText, this.mTextPaint, availableWidth, TextUtils.TruncateAt.END);
                if (this.mTextToDraw == null || !this.mTextToDraw.equals(title)) {
                    this.mTextToDraw = title;
                }

                this.mIsRtl = this.calculateIsRtl(this.mTextToDraw);
                this.mTextWidth = this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length());
            }

            this.mUseTexture = USE_SCALING_TEXTURE && this.mScale != 1.0F;
            if (this.mUseTexture) {
                this.ensureExpandedTexture();
            }

            ViewCompat.postInvalidateOnAnimation(this.mView);
        }
    }

    private void ensureExpandedTexture() {
        if (this.mExpandedTitleTexture == null && !this.mExpandedBounds.isEmpty() && !TextUtils.isEmpty(this.mTextToDraw)) {
            this.mTextPaint.setTextSize(this.mExpandedTextSize);
            this.mTextPaint.setColor(this.mExpandedTextColor);
            int w = Math.round(this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length()));
            int h = Math.round(this.mTextPaint.descent() - this.mTextPaint.ascent());
            this.mTextWidth = (float) w;
            if (w > 0 || h > 0) {
                this.mExpandedTitleTexture = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(this.mExpandedTitleTexture);
                c.drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), 0.0F, (float) h - this.mTextPaint.descent(), this.mTextPaint);
                if (this.mTexturePaint == null) {
                    this.mTexturePaint = new Paint();
                    this.mTexturePaint.setAntiAlias(true);
                    this.mTexturePaint.setFilterBitmap(true);
                }

            }
        }
    }

    public void recalculate() {
        if (this.mView.getHeight() > 0 && this.mView.getWidth() > 0) {
            this.calculateBaselines();
            this.calculateOffsets();
        }

    }

    public void setText(CharSequence text) {
        if (text == null || !text.equals(this.mText)) {
            this.mText = text;
            this.mTextToDraw = null;
            this.clearTexture();
            this.recalculate();
        }

    }

    public CharSequence getText() {
        return this.mText;
    }

    private void clearTexture() {
        if (this.mExpandedTitleTexture != null) {
            this.mExpandedTitleTexture.recycle();
            this.mExpandedTitleTexture = null;
        }

    }

    private static boolean isClose(float value, float targetValue) {
        return Math.abs(value - targetValue) < 0.0010F;
    }

    public int getExpandedTextColor() {
        return this.mExpandedTextColor;
    }

    public int getCollapsedTextColor() {
        return this.mCollapsedTextColor;
    }

    private static int blendColors(int color1, int color2, float ratio) {
        float inverseRatio = 1.0F - ratio;
        float a = (float) Color.alpha(color1) * inverseRatio + (float) Color.alpha(color2) * ratio;
        float r = (float) Color.red(color1) * inverseRatio + (float) Color.red(color2) * ratio;
        float g = (float) Color.green(color1) * inverseRatio + (float) Color.green(color2) * ratio;
        float b = (float) Color.blue(color1) * inverseRatio + (float) Color.blue(color2) * ratio;
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

    private static float interpolate(float startValue, float endValue, float fraction, Interpolator interpolator) {
        if (interpolator != null) {
            fraction = interpolator.getInterpolation(fraction);
        }

        return AnimationUtils.lerp(startValue, endValue, fraction);
    }

    static {
        USE_SCALING_TEXTURE = Build.VERSION.SDK_INT < 18;
    }


    private static class MathUtils {

        MathUtils() {
        }

        public static int constrain(int amount, int low, int high) {
            return amount < low ? low : (amount > high ? high : amount);
        }

        static float constrain(float amount, float low, float high) {
            return amount < low ? low : (amount > high ? high : amount);
        }
    }


    public static class AnimationUtils {

        public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
        public static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();
        public static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

        AnimationUtils() {
        }

        static float lerp(float startValue, float endValue, float fraction) {
            return startValue + fraction * (endValue - startValue);
        }

        public static int lerp(int startValue, int endValue, float fraction) {
            return startValue + Math.round(fraction * (float) (endValue - startValue));
        }

        public static class AnimationListenerAdapter implements Animation.AnimationListener {

            AnimationListenerAdapter() {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }
        }
    }
}
