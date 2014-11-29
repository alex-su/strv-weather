package com.alexsu.weather.android.client.command;

import android.location.Location;

import com.alexsu.weather.android.client.parser.GetForecastParser;
import com.alexsu.weather.android.client.request.RequestFactory;
import com.alexsu.weather.android.data.WeatherCondition;

import java.util.ArrayList;

public class GetForecastCommand extends Command<ArrayList<WeatherCondition>> {

    public GetForecastCommand(Location location) {
        mRequest = RequestFactory.newForecatsRequest(location);
        mParser = new GetForecastParser();
    }

}
