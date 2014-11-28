package com.alexsu.weather.android.fragment;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.activity.SettingsActivity;
import com.alexsu.weather.android.activity.SettingsActivityHC;
import com.alexsu.weather.android.util.Constants;
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
    public void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startSettingsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_SETTINGS) {
            onSettingsChanged();
        }
    }

    private void startSettingsActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            SettingsActivity.startActivityForResult(getActivity(), this);
        } else {
            SettingsActivityHC.startActivityForResult(getActivity(), this);
        }
    }

    public abstract int getTitleRes();

    protected abstract void onLocationReceived(Location location);

    protected abstract void onLocationError();

    protected abstract void onSettingsChanged();

}
