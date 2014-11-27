package com.alexsu.weather.android.util;

import android.location.Location;

import java.util.Locale;

public class LocationUtil {

    public static String locationToString(Location location) {
        String format = "%f,%f";
        return String.format(Locale.ENGLISH, format, location.getLatitude(), location.getLongitude());
    }

}
