package com.lamcreations.scaffoldsampleapp;

import com.lamcreations.scaffold.Scaffold;
import com.firebase.client.Firebase;


public class SampleApplication extends Scaffold {

    protected static SampleApplication sInstance = null;

    public static SampleApplication getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Firebase.setAndroidContext(this);
    }
}
