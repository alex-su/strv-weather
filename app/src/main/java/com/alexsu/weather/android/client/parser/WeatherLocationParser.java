package com.alexsu.weather.android.client.parser;

import com.alexsu.weather.android.data.WeatherLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherLocationParser {

    public WeatherLocation parseLocation(JSONObject nearestAreaObject) throws JSONException {
        WeatherLocation weatherLocation = new WeatherLocation();
        JSONArray areaNameArray = nearestAreaObject.getJSONArray("areaName");
        if (areaNameArray.length() > 0) {
            weatherLocation.setAreaName(areaNameArray.getJSONObject(0).optString("value"));
        }
        JSONArray countryArray = nearestAreaObject.getJSONArray("country");
        if (countryArray.length() > 0) {
            weatherLocation.setCountryName(countryArray.getJSONObject(0).optString("value"));
        }
        return weatherLocation;
    }

}
