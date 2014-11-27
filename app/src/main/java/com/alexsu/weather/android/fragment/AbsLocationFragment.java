package com.alexsu.weather.android.fragment;

import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.alexsu.weather.android.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

public abstract class AbsLocationFragment extends Fragment implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    protected static final String EXTRA_LOCATION = "location";

    protected LocationClient mLocationClient;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(
                getActivity()) == ConnectionResult.SUCCESS) {
            mLocationClient = new LocationClient(getActivity(), this, this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mLocationClient != null) {
            mLocationClient.connect();
        } else {
            Toast.makeText(getActivity(), R.string.error_location, Toast.LENGTH_SHORT).show();
            onLocationError();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLocationClient != null) {
            mLocationClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle dataBundle) {
        onLocationReceived(mLocationClient.getLastLocation());
    }

    @Override
    public void onDisconnected() {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                if (getActivity() != null) {
                    connectionResult.startResolutionForResult(getActivity(), 0);
                }
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), R.string.error_location, Toast.LENGTH_SHORT).show();
        }
        onLocationError();
    }

    public abstract int getTitleRes();

    protected abstract void onLocationReceived(Location location);

    protected abstract void onLocationError();

}
