package com.alexsu.weather.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.alexsu.weather.android.R;

/**
 * We're using PreferenceActivity instead of PreferenceFragment because the latter requires min. API 11
 * and isn't available in the support library
 */
public class SettingsActivity extends PreferenceActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
