package com.lamcreations.scaffoldsampleapp;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lamcreations.scaffold.users.models.UserAccount;

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
