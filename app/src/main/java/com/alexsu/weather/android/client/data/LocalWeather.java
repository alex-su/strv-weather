package com.alexsu.weather.android.client.data;

public class LocalWeather {

    private WeatherCondition mWeatherCondition;
    private WeatherLocation mWeatherLocation;

    public WeatherCondition getWeatherCondition() {
        return mWeatherCondition;
    }

    public void setWeatherCondition(WeatherCondition weatherCondition) {
        this.mWeatherCondition = weatherCondition;
    }

    public WeatherLocation getWeatherLocation() {
        return mWeatherLocation;
    }

    public void setWeatherLocation(WeatherLocation weatherLocation) {
        this.mWeatherLocation = weatherLocation;
    }
}
