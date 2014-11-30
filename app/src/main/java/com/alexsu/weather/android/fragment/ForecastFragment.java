package com.alexsu.weather.android.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.adapter.ForecastAdapter;
import com.alexsu.weather.android.data.WeatherCondition;
import com.alexsu.weather.android.loader.ForecastLoader;
import com.alexsu.weather.android.util.InternetConnectionUtil;
import com.alexsu.weather.android.util.Settings;
import com.alexsu.weather.android.util.IntentUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ForecastFragment extends AbsLocationFragment implements LoaderManager.LoaderCallbacks<ArrayList<WeatherCondition>> {

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @InjectView(R.id.forecast_list)
    ListView mForecastListView;
    @InjectView(R.id.forecast_progress_layout)
    FrameLayout mProgressLayout;
    @InjectView(R.id.no_internet_view)
    RelativeLayout mNoInternetLayout;
    @InjectView(R.id.no_internet_settings_button)
    Button mInternetSettingsButton;
    @InjectView(R.id.forecast_empty_view)
    TextView mEmptyView;

    private ArrayList<WeatherCondition> mForecastList = new ArrayList<WeatherCondition>();
    private ForecastAdapter mForecastAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_forecast, container, false);
        ButterKnife.inject(this, contentView);
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
        mForecastAdapter = new ForecastAdapter(getActivity(), mForecastList);
        mForecastListView.setAdapter(mForecastAdapter);
        mForecastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Displaying a dialog with sharing options
                WeatherCondition weatherCondition = mForecastList.get(position);
                ShareDialogFragment shareDialogFragment =
                        ShareDialogFragment.newInstance(weatherCondition);
                shareDialogFragment.show(getFragmentManager(), "Share");
            }
        });
        mForecastListView.setEmptyView(mEmptyView);
        checkInternetConnection();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNoInternetLayout.getVisibility() == View.VISIBLE) {
            checkInternetConnection();
        }
    }

    @Override
    public int getTitleRes() {
        return R.string.title_forecast;
    }

    @Override
    protected void onLocationReceived(Location location) {
        if (location == null) {
            onLocationError();
            return;
        }
        showProgress();
        Bundle params = new Bundle();
        params.putParcelable(EXTRA_LOCATION, location);
        getLoaderManager().initLoader(0, params, this).forceLoad();
    }

    @Override
    protected void onLocationError() {
        hideProgress();
    }

    @Override
    protected void onSettingsChanged() {
        mForecastAdapter.updateSettings(Settings.isUsingCelsius(getActivity()));
    }

    @Override
    public Loader<ArrayList<WeatherCondition>> onCreateLoader(int id, Bundle args) {
        Location location = args.getParcelable(EXTRA_LOCATION);
        return new ForecastLoader(getActivity(), location);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeatherCondition>> loader,
                               ArrayList<WeatherCondition> forecastList) {
        hideProgress();
        if (forecastList != null) {
            mForecastList.clear();
            mForecastList.addAll(forecastList);
            mForecastAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<WeatherCondition>> loader) {
        hideProgress();
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

    private void checkInternetConnection() {
        if (!InternetConnectionUtil.isConnected(getActivity())) {
            showNoInternetView();
        } else {
            hideNoInternetView();
        }
    }

}
