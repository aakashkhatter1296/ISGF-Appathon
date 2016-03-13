package com.nsit.hack.energy;

import android.app.Application;
import android.content.Context;

import com.pushbots.push.Pushbots;

/**
 * Created by aman on 13/3/16.
 */
public class App extends Application {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        Pushbots.sharedInstance().init(this);

    }

    public static App getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
