package com.lamcreations.scaffold;

import android.app.Application;


public class ScaffoldApplication extends Application {

    protected static ScaffoldApplication sInstance = null;

    public static ScaffoldApplication getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
