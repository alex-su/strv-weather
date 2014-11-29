package com.alexsu.weather.android.loader;

import android.content.Context;
import android.location.Location;

import com.alexsu.weather.android.client.command.GetTodayWeatherCommand;
import com.alexsu.weather.android.data.LocalWeather;

public class TodayWeatherLoader extends CommandLoader<LocalWeather> {

    public TodayWeatherLoader(Context context, Location location) {
        super(context);
        setCommand(new GetTodayWeatherCommand(location));
    }

}
