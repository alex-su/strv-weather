package com.alexsu.weather.android.data;

public class WeatherLocation {

    private String mAreaName;
    private String mCountryName;

    public String getCountryName() {
        return mCountryName;
    }

    public void setCountryName(String countryName) {
        this.mCountryName = countryName;
    }

    public String getAreaName() {
        return mAreaName;
    }

    public void setAreaName(String areaName) {
        this.mAreaName = areaName;
    }
}
