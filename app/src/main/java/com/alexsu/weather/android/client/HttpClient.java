package com.alexsu.weather.android.client;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class HttpClient extends OkHttpClient {

    private static HttpClient sInstance;

    public static HttpClient getInstance() {
        if (sInstance == null) {
            sInstance = new HttpClient();
            sInstance.setConnectTimeout(10, TimeUnit.SECONDS);
        }
        return sInstance;
    }

}
