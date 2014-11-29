package com.alexsu.weather.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.data.NavigationItem;
import com.alexsu.weather.android.util.FontUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NavigationDrawerAdapter extends ArrayAdapter<NavigationItem> {

    private LayoutInflater mLayoutInflater;

    public NavigationDrawerAdapter(Context context, ArrayList<NavigationItem> navigationItems) {
        super(context, R.layout.list_item_navigation, navigationItems);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_navigation, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.populate(getItem(position));
        return convertView;
    }

    public class ViewHolder {

        @InjectView(R.id.navigation_icon)
        ImageView mNavigationIcon;
        @InjectView(R.id.navigation_label)
        TextView mNavigationLabel;

        public ViewHolder(View convertView) {
            ButterKnife.inject(this, convertView);
            mNavigationLabel.setTypeface(FontUtil.get(getContext(), FontUtil.ROBOTO_REGULAR));
        }

        public void populate(NavigationItem navigationItem) {
            mNavigationIcon.setImageResource(navigationItem.getIconResId());
            mNavigationLabel.setText(navigationItem.getTitleResId());
        }

    }

}
