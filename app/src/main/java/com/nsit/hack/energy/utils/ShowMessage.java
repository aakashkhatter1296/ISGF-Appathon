package com.nsit.hack.energy.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.nsit.hack.energy.App;

/**
 * Created by aman on 13/3/16.
 */
public class ShowMessage {
    static Context context = App.getAppContext();

    public static void toast(String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        Log.d("ShowMessage", string);
    }

    public static void log(String string) {

        Log.d("ShowMessage",string);
    }
}