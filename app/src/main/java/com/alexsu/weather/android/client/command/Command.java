package com.alexsu.weather.android.client.command;

import com.alexsu.weather.android.client.HttpClient;
import com.alexsu.weather.android.client.parser.Parser;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;

public class Command<T> {

    protected Request mRequest;
    protected Parser<T> mParser;

    public T execute() {
        try {
            Response response = HttpClient.getInstance().newCall(mRequest).execute();
            if (response.isSuccessful()) {
                return mParser.parse(response.body());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
