package com.alexsu.weather.android.client.parser;

import com.alexsu.weather.android.client.data.WeatherCondition;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetForecastParser implements Parser<ArrayList<WeatherCondition>> {

    @Override
    public ArrayList<WeatherCondition> parse(ResponseBody responseBody) throws IOException, JSONException {
        ArrayList<WeatherCondition> result = new ArrayList<WeatherCondition>();
        WeatherConditionParser internalParser = new WeatherConditionParser();
        JSONObject jsonObject = new JSONObject(responseBody.string());
        jsonObject = jsonObject.getJSONObject("data");
        JSONArray forecastArray = jsonObject.getJSONArray("weather");
        for (int i = 0; i < forecastArray.length(); i++) {
            JSONObject dailyForecastObject = forecastArray.getJSONObject(i);
            JSONArray hourlyForecastArray = dailyForecastObject.getJSONArray("hourly");
            WeatherCondition weatherCondition = internalParser.parseCondition(
                    hourlyForecastArray.getJSONObject(0));
            weatherCondition.setDate(parseDate(dailyForecastObject.getString("date")));
            result.add(weatherCondition);
        }
        return result;
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
