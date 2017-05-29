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

package com.lamcreations.scaffold.common.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ImagePagerAdapter<T> extends PagerAdapter {

    private List<T> mImages = new ArrayList<>();

    public ImagePagerAdapter() {
    }

    public void setImages(List<T> list) {
        mImages = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void addImage(T image) {
        mImages.add(image);
        notifyDataSetChanged();
    }

    public T getImageUri(int position) {
        return mImages.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        View view = LayoutInflater.from(context).inflate(getLayoutResId(), container, false);
        bindView(view, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_UNCHANGED;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object.equals(view);
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void bindView(View view, int position);
}
