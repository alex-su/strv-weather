package com.alexsu.weather.android.client.parser;

import com.alexsu.weather.android.data.LocalWeather;
import com.alexsu.weather.android.data.WeatherCondition;
import com.alexsu.weather.android.data.WeatherLocation;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetTodayWeatherParser implements Parser<LocalWeather> {

    @Override
    public LocalWeather parse(ResponseBody responseBody) throws IOException, JSONException {
        LocalWeather localWeather = new LocalWeather();
        JSONObject dataObject = new JSONObject(responseBody.string());
        dataObject = dataObject.getJSONObject("data");
        JSONArray conditionsArray = dataObject.getJSONArray("current_condition");
        if (conditionsArray.length() > 0) {
            WeatherCondition weatherCondition = new WeatherConditionParser().parseCondition(
                    conditionsArray.getJSONObject(0));
            localWeather.setWeatherCondition(weatherCondition);
        }
        JSONArray nearestAreaArray = dataObject.getJSONArray("nearest_area");
        if (nearestAreaArray.length() > 0) {
            WeatherLocation weatherLocation = new WeatherLocationParser().parseLocation(
                    nearestAreaArray.getJSONObject(0));
            localWeather.setWeatherLocation(weatherLocation);
        }
        return localWeather;
    }

}
