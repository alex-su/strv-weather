package com.alexsu.weather.android.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.data.LocalWeather;
import com.alexsu.weather.android.data.WeatherCondition;
import com.alexsu.weather.android.data.WeatherLocation;
import com.alexsu.weather.android.loader.TodayWeatherLoader;
import com.alexsu.weather.android.util.FontUtil;
import com.alexsu.weather.android.util.InternetConnectionUtil;
import com.alexsu.weather.android.util.Settings;
import com.alexsu.weather.android.util.IntentUtil;
import com.androidquery.AQuery;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TodayFragment extends AbsLocationFragment implements
        LoaderManager.LoaderCallbacks<LocalWeather>, SwipeRefreshLayout.OnRefreshListener {

    public static TodayFragment newInstance() {
        return new TodayFragment();
    }

    @InjectView(R.id.today_weather_icon)
    ImageView mWeatherIcon;
    @InjectView(R.id.today_location_label)
    TextView mLocationLabel;
    @InjectView(R.id.today_temperature_label)
    TextView mTemperatureLabel;
    @InjectView(R.id.today_humidity_label)
    TextView mHumidityLabel;
    @InjectView(R.id.today_precipitation_label)
    TextView mPrecipitationLabel;
    @InjectView(R.id.today_pressure_label)
    TextView mPressureLabel;
    @InjectView(R.id.today_wind_speed_label)
    TextView mWindSpeedLabel;
    @InjectView(R.id.today_direction_label)
    TextView mWindDirectionLabel;
    @InjectView(R.id.today_progress_layout)
    FrameLayout mProgressLayout;
    @InjectView(R.id.today_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.no_internet_view)
    RelativeLayout mNoInternetLayout;
    @InjectView(R.id.no_internet_settings_button)
    Button mInternetSettingsButton;

    private LocalWeather mLocalWeather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.inject(this, contentView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mInternetSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.openInternetSettings(getActivity());
            }
        });
        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setFonts();
        checkInternetConnection();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Hide mNoInternetLayout if connected
        if (mNoInternetLayout.getVisibility() == View.VISIBLE) {
            checkInternetConnection();
        }
    }

    @Override
    public int getTitleRes() {
        return R.string.title_today;
    }

    @Override
    protected void onLocationReceived(Location location) {
        showProgress();
        loadWeather(location);
    }

    @Override
    protected void onLocationError() {
        hideProgress();
    }

    @Override
    protected void onSettingsChanged() {
        showWeatherData();
    }

    @Override
    public void onRefresh() {
        if (mLocationClient != null && mLocationClient.isConnected()) {
            mSwipeRefreshLayout.setRefreshing(true);
            loadWeather(mLocationClient.getLastLocation());
        }
    }

    @Override
    public Loader<LocalWeather> onCreateLoader(int id, Bundle args) {
        Location location = args.getParcelable(EXTRA_LOCATION);
        return new TodayWeatherLoader(getActivity(), location);
    }

    @Override
    public void onLoadFinished(Loader<LocalWeather> loader, LocalWeather weather) {
        hideProgress();
        mSwipeRefreshLayout.setRefreshing(false);
        mLocalWeather = weather;
        showWeatherData();
    }

    @Override
    public void onLoaderReset(Loader<LocalWeather> loader) {
    }

    private void loadWeather(Location location) {
        if (location == null) {
            onLocationError();
            return;
        }
        Bundle params = new Bundle();
        params.putParcelable(EXTRA_LOCATION, location);
        getLoaderManager().restartLoader(0, params, this).forceLoad();
    }

    private void showWeatherData() {
        if (mLocalWeather != null) {
            if (mLocalWeather.getWeatherLocation() != null) {
                showWeatherLocation(mLocalWeather.getWeatherLocation());
            }
            if (mLocalWeather.getWeatherCondition() != null) {
                showWeatherCondition(mLocalWeather.getWeatherCondition());
            }
        }
    }

    private void showWeatherLocation(WeatherLocation weatherLocation) {
        mLocationLabel.setText(getString(R.string.format_location,
                weatherLocation.getAreaName(),
                weatherLocation.getCountryName()));
    }

    private void showWeatherCondition(WeatherCondition weatherCondition) {
        mHumidityLabel.setText(getString(R.string.format_humidity,
                weatherCondition.getHumidity()));
        mPrecipitationLabel.setText(getString(R.string.format_precipitation,
                weatherCondition.getPrecipitation()));
        mPressureLabel.setText(getString(R.string.format_pressure,
                weatherCondition.getPressure()));
        mWindDirectionLabel.setText(weatherCondition.getWindDirection());

        showTemperature(weatherCondition);
        showWindSpeed(weatherCondition);

        if (!TextUtils.isEmpty(weatherCondition.getIconUrl())) {
            loadWeatherIcon(weatherCondition.getIconUrl());
        }
    }

    private void showTemperature(WeatherCondition weatherCondition) {
        if (Settings.isUsingCelsius(getActivity())) {
            mTemperatureLabel.setText(getString(R.string.format_temperature_celsius,
                    weatherCondition.getTemperatureCelsius(),
                    weatherCondition.getDescription()));
        } else {
            mTemperatureLabel.setText(getString(R.string.format_temperature_fahrenheit,
                    weatherCondition.getTemperatureFahrenheit(),
                    weatherCondition.getDescription()));
        }
    }

    private void showWindSpeed(WeatherCondition weatherCondition) {
        if (Settings.isUsingMeters(getActivity())) {
            mWindSpeedLabel.setText(getString(R.string.format_speed_kmph,
                    weatherCondition.getWindSpeedKmph()));
        } else {
            mWindSpeedLabel.setText(getString(R.string.format_speed_miles,
                    weatherCondition.getWindSpeedMiles()));
        }
    }

    private void loadWeatherIcon(String url) {
        AQuery aq = new AQuery(getActivity());
        // Async loading with file and memory cache
        aq.id(mWeatherIcon).image(url, true, true);
    }

    private void showProgress() {
        mProgressLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        mProgressLayout.setVisibility(View.GONE);
    }

    private void showNoInternetView() {
        mNoInternetLayout.setVisibility(View.VISIBLE);
    }

    private void hideNoInternetView() {
        mNoInternetLayout.setVisibility(View.GONE);
    }

    private void setFonts() {
        mLocationLabel.setTypeface(FontUtil.get(getActivity(), FontUtil.ROBOTO_LIGHT));
        mTemperatureLabel.setTypeface(FontUtil.get(getActivity(), FontUtil.ROBOTO_MEDIUM));
        mHumidityLabel.setTypeface(FontUtil.get(getActivity(), FontUtil.ROBOTO_MEDIUM));
        mPrecipitationLabel.setTypeface(FontUtil.get(getActivity(), FontUtil.ROBOTO_MEDIUM));
        mPressureLabel.setTypeface(FontUtil.get(getActivity(), FontUtil.ROBOTO_MEDIUM));
        mWindSpeedLabel.setTypeface(FontUtil.get(getActivity(), FontUtil.ROBOTO_MEDIUM));
        mWindDirectionLabel.setTypeface(FontUtil.get(getActivity(), FontUtil.ROBOTO_MEDIUM));
    }

    private void checkInternetConnection() {
        if (!InternetConnectionUtil.isConnected(getActivity())) {
            showNoInternetView();
        } else {
            hideNoInternetView();
        }
    }

}
