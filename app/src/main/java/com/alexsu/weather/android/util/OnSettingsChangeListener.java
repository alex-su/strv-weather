package com.alexsu.weather.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import com.alexsu.weather.android.R;

public class OnSettingsChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {

    private Context mContext;
    private Preference mLengthPref;
    private Preference mTemperaturePref;

    public OnSettingsChangeListener(Context context, Preference lengthPref, Preference temperaturePref) {
        mContext = context;
        mLengthPref = lengthPref;
        mTemperaturePref = temperaturePref;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (mContext != null) {
            String lengthKey = mContext.getString(R.string.pref_key_length);
            String temperatureKey = mContext.getString(R.string.pref_key_temperature);
            if (key.equals(lengthKey)) {
                Preference lengthPref = mLengthPref;
                if (Settings.isUsingMeters(mContext)) {
                    lengthPref.setSummary(R.string.pref_length_entry_meters);
                } else {
                    lengthPref.setSummary(R.string.pref_length_entry_miles);
                }
            } else if (key.equals(temperatureKey)) {
                Preference temperaturePref = mTemperaturePref;
                if (Settings.isUsingCelsius(mContext)) {
                    temperaturePref.setSummary(R.string.pref_temperature_entry_celsius);
                } else {
                    temperaturePref.setSummary(R.string.pref_temperature_entry_fahrenheit);
                }
            }
        }
    }

}
