package com.alexsu.weather.android.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.util.OnSettingsChangeListener;
import com.alexsu.weather.android.util.Settings;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsFragment extends PreferenceFragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
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
                getActivity(), lengthPref, temperaturePref);
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
        lengthPref.setSummary(Settings.isUsingMeters(getActivity()) ?
                R.string.pref_length_entry_meters :
                R.string.pref_length_entry_miles);
        temperaturePref.setSummary(Settings.isUsingCelsius(getActivity()) ?
                R.string.pref_temperature_entry_celsius :
                R.string.pref_temperature_entry_fahrenheit);
    }

}
