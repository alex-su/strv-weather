package com.alexsu.weather.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexsu.weather.android.R;

public class TodayFragment extends AbsNavigationFragment {

    public static TodayFragment newInstance() {
        return new TodayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today, null);
    }

    @Override
    public int getTitleRes() {
        return R.string.title_today;
    }
}
