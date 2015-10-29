package com.lamcreations.scaffold.users.models;


import android.content.Context;
import android.graphics.Bitmap;

public interface UserAccount {
    String getName();

    String getEmail();

    void getProfileImage(Context context, ImageCallback callback);

    void getHeaderImage(Context context, ImageCallback callback);

    interface ImageCallback {
        void onImageAvailable(Bitmap image);
    }
}
