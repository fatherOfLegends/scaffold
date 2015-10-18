package com.lamcreations.scaffoldsampleapp;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lamcreations.scaffold.users.UserAccount;

import android.content.Context;
import android.graphics.Bitmap;


public class SampleUserAccount implements UserAccount {

    @Override
    public String getName() {
        return "Larry McKenzie";
    }

    @Override
    public String getEmail() {
        return "lmckenzie@ebay.com";
    }

    @Override
    public void getProfileImage(Context context, final UserAccount.ImageCallback callback) {
        Glide.with(context)
                .load("https://en.gravatar.com/userimage/69170069/01110c8bb8204856f0616fdcfbd01ebe.jpg?size=200")
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        callback.onImageAvailable(resource);
                    }
                });
    }

    @Override
    public void getHeaderImage(Context context, final UserAccount.ImageCallback callback) {
        Glide.with(context)
                .load("https://pixabay.com/static/uploads/photo/2014/09/08/04/35/brick-438823_640.jpg")
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        callback.onImageAvailable(resource);
                    }
                });
    }
}
