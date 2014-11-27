package com.alexsu.weather.android.client;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class HttpClient extends OkHttpClient {

    private static HttpClient mInstance;

    public static HttpClient getInstance() {
        if (mInstance == null) {
            mInstance = new HttpClient();
            mInstance.setConnectTimeout(10, TimeUnit.SECONDS);
        }
        return mInstance;
    }

}
