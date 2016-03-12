package com.nsit.hack.energy;

import android.app.Application;
import android.content.Context;

/**
 * Created by aman on 13/3/16.
 */
public class App extends Application {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

    }

    public static App getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
