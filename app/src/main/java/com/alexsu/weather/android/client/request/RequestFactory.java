package com.alexsu.weather.android.client.request;

import android.location.Location;
import android.net.Uri;

import com.alexsu.weather.android.StrvWeatherConfig;
import com.alexsu.weather.android.util.LocationUtil;
import com.squareup.okhttp.Request;

public class RequestFactory {

    public static Request newTodayWeatherRequest(Location location) {
        Uri uri = getCommonUri(location).buildUpon()
                .appendQueryParameter("fx", "no")
                .appendQueryParameter("includelocation", "yes")
                .build();
        return newRequest(uri.toString());
    }

    public static Request newForecatsRequest(Location location) {
        Uri uri = getCommonUri(location).buildUpon()
                .appendQueryParameter("num_of_days", "7")
                .appendQueryParameter("cc", "no")
                .appendQueryParameter("tp", "24")
                .build();
        return newRequest(uri.toString());
    }

    private static Uri getCommonUri(Location location) {
        return Uri.parse(StrvWeatherConfig.WEATHER_API_URL).buildUpon()
                .appendQueryParameter("q", LocationUtil.locationToString(location))
                .appendQueryParameter("format", "json")
                .appendQueryParameter("key", StrvWeatherConfig.WEATHER_API_KEY)
                .build();
    }

    private static Request newRequest(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }

}
