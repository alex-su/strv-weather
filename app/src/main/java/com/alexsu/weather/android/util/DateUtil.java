package com.alexsu.weather.android.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getDayOfWeek(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        return dateFormat.format(date);
    }

}
