package com.alexsu.weather.android.util;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import java.util.HashMap;

public class FontUtil {

    public static String ROBOTO_REGULAR = "Roboto-Regular.ttf";
    public static String ROBOTO_MEDIUM = "Roboto-Medium.ttf";
    public static String ROBOTO_LIGHT = "Roboto-Light.ttf";

    private static final HashMap<String, Typeface> sCache = new HashMap<String, Typeface>();

    public static Typeface get(Context context, String typefacePath) {
        if (!sCache.containsKey(typefacePath)) {
            try {
                Typeface t = Typeface.createFromAsset(context.getAssets(), typefacePath);
                sCache.put(typefacePath, t);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return sCache.get(typefacePath);
    }

    public static Spannable applyTypefaceSpan(Context context, int textResId, String typefacePath) {
        Typeface typeface = get(context, typefacePath);
        String text = context.getString(textResId);
        CustomTypefaceSpan typefaceSpan = new CustomTypefaceSpan("", typeface);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        spannableStringBuilder.setSpan(typefaceSpan, 0, text.length() - 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }
}