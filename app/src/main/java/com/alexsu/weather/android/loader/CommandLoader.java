package com.alexsu.weather.android.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.alexsu.weather.android.client.command.Command;

public class CommandLoader<T> extends AsyncTaskLoader<T> {

    private Command<T> mCommand;

    public CommandLoader(Context context) {
        super(context);
    }

    @Override
    public T loadInBackground() {
        return mCommand.execute();
    }

    protected void setCommand(Command<T> command) {
        mCommand = command;
    }

}
