package com.alexsu.weather.android.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.data.WeatherCondition;
import com.alexsu.weather.android.util.DateUtil;
import com.alexsu.weather.android.util.FontUtil;
import com.alexsu.weather.android.util.Settings;
import com.androidquery.AQuery;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ForecastAdapter extends ArrayAdapter<WeatherCondition> {

    private LayoutInflater mLayoutInflater;
    private Resources mResources;
    private AQuery mAQuery;

    private boolean mIsUsingCelsius;

    public ForecastAdapter(Context context, ArrayList<WeatherCondition> forecastList) {
        super(context, R.layout.list_item_forecast, forecastList);
        mLayoutInflater = LayoutInflater.from(context);
        mResources = context.getResources();
        mAQuery = new AQuery(context);
        mIsUsingCelsius = Settings.isUsingCelsius(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_forecast, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.populate(getItem(position));
        return convertView;
    }

    public void updateSettings(boolean isUsingCelsius) {
        mIsUsingCelsius = isUsingCelsius;
        notifyDataSetChanged();
    }

    public class ViewHolder {

        @InjectView(R.id.forecast_weather_icon)
        ImageView mWeatherIcon;
        @InjectView(R.id.forecast_day_label)
        TextView mDayLabel;
        @InjectView(R.id.forecast_temperature_label)
        TextView mTemperatureLabel;
        @InjectView(R.id.forecast_conditions_label)
        TextView mConditionsLabel;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
            setFonts();
        }

        public void populate(WeatherCondition weatherCondition) {
            String temperatureString;
            if (mIsUsingCelsius) {
                temperatureString = mResources.getString(
                        R.string.format_temperature_celsius_short,
                        weatherCondition.getTemperatureCelsius());
            }  else {
                temperatureString = mResources.getString(
                        R.string.format_temperature_fahrenheit_short,
                        weatherCondition.getTemperatureFahrenheit());
            }
            mTemperatureLabel.setText(temperatureString);
            mDayLabel.setText(DateUtil.getDayOfWeek(weatherCondition.getDate()));
            mConditionsLabel.setText(weatherCondition.getDescription());
            if (weatherCondition.getIconUrl() != null) {
                mAQuery.id(mWeatherIcon).image(weatherCondition.getIconUrl(), true, true);
            }
        }

        private void setFonts() {
            mDayLabel.setTypeface(FontUtil.get(getContext(), FontUtil.ROBOTO_LIGHT));
            mTemperatureLabel.setTypeface(FontUtil.get(getContext(), FontUtil.ROBOTO_MEDIUM));
            mConditionsLabel.setTypeface(FontUtil.get(getContext(), FontUtil.ROBOTO_LIGHT));
        }

    }

}
