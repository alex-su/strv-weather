package com.alexsu.weather.android;

import android.app.Application;
import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class StrvWeatherApplication extends Application {

    private static StrvWeatherApplication mInstance;

    public StrvWeatherApplication() {
        mInstance = this;
    }

    public static Context getContext() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // force AsyncTask to be initialized in the main thread due to the bug:
        // http://stackoverflow.com/questions/4280330/onpostexecute-not-being-called-in-asynctask-handler-runtime-exception
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
