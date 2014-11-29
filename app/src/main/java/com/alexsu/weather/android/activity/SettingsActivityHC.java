package com.alexsu.weather.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.fragment.SettingsFragment;
import com.alexsu.weather.android.util.Constants;
import com.alexsu.weather.android.util.FontUtil;

/**
 * SettingsActivity for API >= 11
 */
public class SettingsActivityHC extends ActionBarActivity {

    public static void startActivityForResult(Context context, Fragment fromFragment) {
        Intent intent = new Intent(context, SettingsActivityHC.class);
        fromFragment.startActivityForResult(intent, Constants.REQUEST_CODE_SETTINGS);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_hc);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_container, SettingsFragment.newInstance())
                    .commit();
        }

        updateActionBarTitle();
    }

    private void updateActionBarTitle() {
        Spannable actionBarTitle = FontUtil.applyTypefaceSpan(this,
                R.string.title_settings, FontUtil.ROBOTO_REGULAR);
        getSupportActionBar().setTitle(actionBarTitle);
    }

}
