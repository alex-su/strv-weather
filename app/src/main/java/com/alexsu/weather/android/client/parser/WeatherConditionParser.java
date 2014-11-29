package com.alexsu.weather.android.client.parser;

import com.alexsu.weather.android.data.WeatherCondition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherConditionParser {

    public WeatherCondition parseCondition(JSONObject jsonObject) throws JSONException {
        WeatherCondition weatherCondition = new WeatherCondition();
        weatherCondition.setHumidity(jsonObject.optInt("humidity"));
        weatherCondition.setPrecipitation(jsonObject.optDouble("precipMM"));
        weatherCondition.setPressure(jsonObject.optInt("pressure"));
        weatherCondition.setWindDirection(jsonObject.optString("winddir16Point"));
        weatherCondition.setWindSpeedKmph(jsonObject.optInt("windspeedKmph"));
        weatherCondition.setWindSpeedMiles(jsonObject.optInt("windspeedMiles"));
        parseTemperature(jsonObject, weatherCondition);
        JSONArray weatherDescriptionArray = jsonObject.optJSONArray("weatherDesc");
        if (weatherDescriptionArray != null && weatherDescriptionArray.length() > 0) {
            weatherCondition.setDescription(weatherDescriptionArray.getJSONObject(0).getString("value"));
        }
        JSONArray weatherIconsArray = jsonObject.optJSONArray("weatherIconUrl");
        if (weatherIconsArray != null && weatherIconsArray.length() > 0) {
            weatherCondition.setIconUrl(weatherIconsArray.getJSONObject(0).getString("value"));
        }
        return weatherCondition;
    }

    private void parseTemperature(JSONObject jsonObject, WeatherCondition weatherCondition) {
        String temperatureCelsiusKey = jsonObject.has("temp_C") ? "temp_C" : "tempC";
        String temperatureFahrenheitKey = jsonObject.has("temp_F") ? "temp_F" : "tempF";
        weatherCondition.setTemperatureCelsius(jsonObject.optInt(temperatureCelsiusKey));
        weatherCondition.setTemperatureFahrenheit(jsonObject.optInt(temperatureFahrenheitKey));
    }

}
