package com.lamcreations.scaffold;

import android.app.Application;


public class Scaffold extends Application {

    protected static Scaffold sInstance = null;

    public static Scaffold getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
