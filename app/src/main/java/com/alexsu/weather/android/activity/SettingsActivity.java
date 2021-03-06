package com.alexsu.weather.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.util.Constants;
import com.alexsu.weather.android.util.OnSettingsChangeListener;
import com.alexsu.weather.android.util.Settings;

/**
 * SettingsActivity for API < 11
 */
@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity {

    public static void startActivityForResult(Context context, Fragment fromFragment) {
        Intent intent = new Intent(context, SettingsActivity.class);
        fromFragment.startActivityForResult(intent, Constants.REQUEST_CODE_SETTINGS);
    }

    private OnSettingsChangeListener mOnSettingsChangeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        Preference lengthPref = findPreference(getString(R.string.pref_key_length));
        Preference temperaturePref = findPreference(getString(R.string.pref_key_temperature));
        showCurrentValues(lengthPref, temperaturePref);
        mOnSettingsChangeListener = new OnSettingsChangeListener(
                this, lengthPref, temperaturePref);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(mOnSettingsChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(mOnSettingsChangeListener);
    }

    private void showCurrentValues(Preference lengthPref, Preference temperaturePref) {
        lengthPref.setSummary(Settings.isUsingMeters(this) ?
                R.string.pref_length_entry_meters :
                R.string.pref_length_entry_miles);
        temperaturePref.setSummary(Settings.isUsingCelsius(this) ?
                R.string.pref_temperature_entry_celsius :
                R.string.pref_temperature_entry_fahrenheit);
    }

}
