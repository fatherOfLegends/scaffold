package com.lamcreations.scaffold.common.adapters;

import com.lamcreations.scaffold.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class ImagePagerAdapter extends PagerAdapter {

    private List<String> mImageUris = new ArrayList<>();

    public ImagePagerAdapter() {
    }

    public void setImageUris(List<String> list) {
        mImageUris = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void addImageUri(String imageUri) {
        mImageUris.add(imageUri);
        notifyDataSetChanged();
    }

    public String getImageUri(int position){
        return mImageUris.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(R.layout.image_view, container, false);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_UNCHANGED;
    }

    @Override
    public int getCount() {
        return mImageUris.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object.equals(view);
    }
}
