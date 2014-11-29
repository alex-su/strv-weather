package com.alexsu.weather.android.util;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * TypefaceSpan which supports custom typefaces
 * http://stackoverflow.com/a/10740758/2020889
 */
public class CustomTypefaceSpan extends TypefaceSpan {

    private final Typeface mTypeface;

    public CustomTypefaceSpan(String family, Typeface typeface) {
        super(family);
        mTypeface = typeface;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        applyCustomTypeFace(ds, mTypeface);
    }

    @Override
    public void updateMeasureState(@NonNull TextPaint paint) {
        applyCustomTypeFace(paint, mTypeface);
    }

    private static void applyCustomTypeFace(Paint paint, Typeface tf) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
    }
}
