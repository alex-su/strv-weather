package com.alexsu.weather.android.client.command;

import android.location.Location;

import com.alexsu.weather.android.client.parser.GetTodayWeatherParser;
import com.alexsu.weather.android.client.request.RequestFactory;
import com.alexsu.weather.android.data.LocalWeather;

public class GetTodayWeatherCommand extends Command<LocalWeather> {

    public GetTodayWeatherCommand(Location location) {
        mRequest = RequestFactory.newTodayWeatherRequest(location);
        mParser = new GetTodayWeatherParser();
    }

}
