package com.alexsu.weather.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alexsu.weather.android.R;

/**
 * Storage utility based on SharedPreferences
 */
public class Settings {

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isUsingMeters(Context context) {
        String lengthKey = context.getString(R.string.pref_key_length);
        String lengthValueMeters = context.getString(R.string.pref_length_value_meters);
        SharedPreferences prefs = getSharedPreferences(context);
        return lengthValueMeters.equals(prefs.getString(lengthKey, lengthValueMeters));
    }

    public static boolean isUsingCelsius(Context context) {
        String temperatureKey = context.getString(R.string.pref_key_temperature);
        String temperatureValueCelsius = context.getString(R.string.pref_temperature_value_celsius);
        SharedPreferences prefs = getSharedPreferences(context);
        return temperatureValueCelsius.equals(prefs.getString(temperatureKey, temperatureValueCelsius));
    }

}
