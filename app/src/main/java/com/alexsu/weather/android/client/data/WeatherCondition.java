package com.alexsu.weather.android.client.data;

import java.util.Date;

public class WeatherCondition {

    private int mHumidity;
    private double mPrecipitation;
    private int mPressure;
    private int mTemperatureCelsius;
    private int mTemperatureFahrenheit;
    private int mWindSpeedKmph;
    private int mWindSpeedMiles;
    private String mWindDirection;
    private String mDescription;
    private String mIconUrl;
    private Date mDate;

    public int getHumidity() {
        return mHumidity;
    }

    public void setHumidity(int humidity) {
        this.mHumidity = humidity;
    }

    public double getPrecipitation() {
        return mPrecipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.mPrecipitation = precipitation;
    }

    public int getPressure() {
        return mPressure;
    }

    public void setPressure(int pressure) {
        this.mPressure = pressure;
    }

    public int getTemperatureCelsius() {
        return mTemperatureCelsius;
    }

    public void setTemperatureCelsius(int temperatureCelsius) {
        this.mTemperatureCelsius = temperatureCelsius;
    }

    public int getTemperatureFahrenheit() {
        return mTemperatureFahrenheit;
    }

    public void setTemperatureFahrenheit(int temperatureFahrenheit) {
        this.mTemperatureFahrenheit = temperatureFahrenheit;
    }

    public int getWindSpeedKmph() {
        return mWindSpeedKmph;
    }

    public void setWindSpeedKmph(int windspeedKmph) {
        this.mWindSpeedKmph = windspeedKmph;
    }

    public int getWindSpeedMiles() {
        return mWindSpeedMiles;
    }

    public void setWindSpeedMiles(int windspeedMiles) {
        this.mWindSpeedMiles = windspeedMiles;
    }

    public String getWindDirection() {
        return mWindDirection;
    }

    public void setWindDirection(String windDirection) {
        this.mWindDirection = windDirection;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }
}
