package com.alexsu.weather.android;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;

import java.io.File;

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
        initAndroidQuery();

        // force AsyncTask to be initialized in the main thread due to the bug:
        // http://stackoverflow.com/questions/4280330/onpostexecute-not-being-called-in-asynctask-handler-runtime-exception
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        BitmapAjaxCallback.clearCache();
    }

    private void initAndroidQuery() {
        // Setting cache directory on External Storage
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File cacheDir = getApplicationContext().getExternalCacheDir();
            AQUtility.setCacheDir(cacheDir);
        }
    }

}
