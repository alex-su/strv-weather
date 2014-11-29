package com.alexsu.weather.android.loader;

import android.content.Context;
import android.location.Location;

import com.alexsu.weather.android.client.command.GetForecastCommand;
import com.alexsu.weather.android.data.WeatherCondition;

import java.util.ArrayList;

public class ForecastLoader extends CommandLoader<ArrayList<WeatherCondition>> {

    public ForecastLoader(Context context, Location location) {
        super(context);
        setCommand(new GetForecastCommand(location));
    }

}
