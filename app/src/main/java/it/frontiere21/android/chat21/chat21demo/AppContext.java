package it.frontiere21.android.chat21.chat21demo;

import android.support.multidex.MultiDexApplication;

/**
 * Created by stefanodp91 on 25/09/17.
 */

public class AppContext extends MultiDexApplication {
    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppContext getInstance() {

        return instance;
    }
}