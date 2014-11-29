package com.alexsu.weather.android.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.view.Menu;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.fragment.AbsLocationFragment;
import com.alexsu.weather.android.fragment.ForecastFragment;
import com.alexsu.weather.android.fragment.NavigationDrawerFragment;
import com.alexsu.weather.android.fragment.TodayFragment;
import com.alexsu.weather.android.util.FontUtil;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        AbsLocationFragment fragment = getFragmentForPosition(position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        updateActionBarTitle(fragment);
    }

    private AbsLocationFragment getFragmentForPosition(int position) {
        switch (position) {
            case 1:
                return ForecastFragment.newInstance();
            default:
                return TodayFragment.newInstance();

        }
    }

    private void updateActionBarTitle(AbsLocationFragment fragment) {
        // Applying custom typeface to the ActionBar title
        Spannable actionBarTitle = FontUtil.applyTypefaceSpan(this,
                fragment.getTitleRes(), FontUtil.ROBOTO_REGULAR);
        getSupportActionBar().setTitle(actionBarTitle);
    }

}
