package com.alexsu.weather.android.client.parser;

import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;

import java.io.IOException;

public interface Parser<T> {

    public T parse(ResponseBody responseBody) throws IOException, JSONException;

}
