package com.alexsu.weather.android.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.data.WeatherCondition;

public class IntentUtil {

    public static void share(Context context, WeatherCondition weatherCondition) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getText(context, weatherCondition));
        intent.setType("text/plain");
        context.startActivity(createChooser(context, intent));
    }

    public static void shareViaEmail(Context context, WeatherCondition weatherCondition) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getText(context, weatherCondition));
        context.startActivity(intent);
    }

    public static void shareViaSms(Context context, WeatherCondition weatherCondition, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        intent.putExtra("sms_body", getText(context, weatherCondition));
        context.startActivity(intent);
    }

    public static void pickContact(Fragment fromFragment) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        fromFragment.startActivityForResult(contactPickerIntent, Constants.REQUEST_CODE_PICK_CONTACT);
    }

    public static void openInternetSettings(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
    }

    private static String getText(Context context, WeatherCondition weatherCondition) {
        return context.getString(R.string.share_content_text,
                DateUtil.getDayOfWeek(weatherCondition.getDate()),
                getTemperatureText(context, weatherCondition),
                weatherCondition.getDescription());
    }

    private static String getTemperatureText(Context context, WeatherCondition weatherCondition) {
        String temperatureText;
        if (Settings.isUsingCelsius(context)) {
            temperatureText = context.getString(R.string.format_temperature_celsius_short,
                    weatherCondition.getTemperatureCelsius());
        } else {
            temperatureText = context.getString(R.string.format_temperature_fahrenheit_short,
                    weatherCondition.getTemperatureFahrenheit());
        }
        return temperatureText;
    }

    private static Intent createChooser(Context context, Intent targetIntent) {
        return Intent.createChooser(targetIntent,
                context.getString(R.string.share_dialog_chooser_title));
    }

}
