package com.alexsu.weather.android.fragment;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexsu.weather.android.R;

public class ForecastFragment extends AbsLocationFragment {

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast, null);
    }

    @Override
    public int getTitleRes() {
        return R.string.title_forecast;
    }

    @Override
    protected void onLocationReceived(Location location) {

    }

    @Override
    protected void onLocationError() {

    }
}
