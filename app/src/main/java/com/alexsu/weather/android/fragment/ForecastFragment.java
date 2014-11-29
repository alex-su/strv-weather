package com.alexsu.weather.android.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.adapter.ForecastAdapter;
import com.alexsu.weather.android.client.command.GetForecastCommand;
import com.alexsu.weather.android.data.WeatherCondition;
import com.alexsu.weather.android.util.Settings;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ForecastFragment extends AbsLocationFragment implements LoaderManager.LoaderCallbacks<ArrayList<WeatherCondition>> {

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @InjectView(R.id.forecast_list)
    ListView mForecastListView;
    @InjectView(R.id.forecats_progress_layout)
    FrameLayout mProgressLayout;

    private ArrayList<WeatherCondition> mForecastList = new ArrayList<WeatherCondition>();
    private ForecastAdapter mForecastAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_forecast, null);
        ButterKnife.inject(this, contentView);
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
                WeatherCondition weatherCondition = mForecastList.get(position);
                ShareDialogFragment shareDialogFragment =
                        ShareDialogFragment.newInstance(weatherCondition);
                shareDialogFragment.show(getFragmentManager(), "Share");
            }
        });
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
        final Location location = args.getParcelable(EXTRA_LOCATION);
        return new AsyncTaskLoader<ArrayList<WeatherCondition>>(getActivity()) {
            @Override
            public ArrayList<WeatherCondition> loadInBackground() {
                return new GetForecastCommand(location).execute();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<WeatherCondition>> loader,
                               ArrayList<WeatherCondition> forecastList) {
        hideProgress();
        mForecastList.clear();
        mForecastList.addAll(forecastList);
        mForecastAdapter.notifyDataSetChanged();
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

}
