package com.alexsu.weather.android.data;

public class NavigationItem {

    private int mTitleResId;
    private int mIconResId;

    public NavigationItem(int titleResId, int iconResId) {
        mTitleResId = titleResId;
        mIconResId = iconResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public void setTitleResId(int titleResId) {
        this.mTitleResId = titleResId;
    }

    public int getIconResId() {
        return mIconResId;
    }

    public void setIconResId(int iconResId) {
        this.mIconResId = iconResId;
    }
}
