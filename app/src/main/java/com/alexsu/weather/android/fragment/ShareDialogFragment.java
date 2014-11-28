package com.alexsu.weather.android.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alexsu.weather.android.R;
import com.alexsu.weather.android.client.data.WeatherCondition;
import com.alexsu.weather.android.util.Constants;
import com.alexsu.weather.android.util.ShareUtil;

import java.util.ArrayList;

public class ShareDialogFragment extends DialogFragment {

    private static final String EXTRA_WEATHER = "extra_weather";
    private static final int POSITION_SIMPLE = 0;
    private static final int POSITION_EMAIL = 1;
    private static final int POSITION_SMS = 2;

    private ArrayAdapter<String> mAdapter;
    private WeatherCondition mWeatherCondition;

    public static ShareDialogFragment newInstance(WeatherCondition weatherCondition) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_WEATHER, weatherCondition);
        ShareDialogFragment fragment = new ShareDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = getAdapter(getValues());
        mWeatherCondition = (WeatherCondition) getArguments().getSerializable(EXTRA_WEATHER);
    }

    @Override
    @android.support.annotation.NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.share_dialog_title)
                .setView(getListView())
                .create();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_PICK_CONTACT && resultCode == Activity.RESULT_OK) {
            // A contact has been picked - trying to get his phone number
            String id = data.getData().getLastPathSegment();
            Cursor cursor = getActivity().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.CommonDataKinds.Phone._ID + " = ?",
                    new String[]{id},
                    null);
            int phoneColumnsIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            if (cursor.moveToFirst()) {
                ShareUtil.shareViaSms(getActivity(), mWeatherCondition, cursor.getString(phoneColumnsIndex));
            }
        }
        dismiss();
    }

    private ListView getListView() {
        // We need this instead of AlertDialogBuilder.setAdapter(...) to avoid auto-dismiss on click
        ListView listView = new ListView(getActivity());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long ld) {
                switch (position) {
                    case POSITION_SIMPLE:
                        ShareUtil.share(getActivity(), mWeatherCondition);
                        dismiss();
                        break;
                    case POSITION_EMAIL:
                        ShareUtil.shareViaEmail(getActivity(), mWeatherCondition);
                        dismiss();
                        break;
                    case POSITION_SMS:
                        ShareUtil.pickContact(ShareDialogFragment.this);
                        break;
                }
            }
        });
        return listView;
    }

    private ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<String>();
        values.add(POSITION_SIMPLE, getActivity().getString(R.string.share_dialog_item_simple));
        values.add(POSITION_EMAIL, getActivity().getString(R.string.share_dialog_item_email));
        values.add(POSITION_SMS, getActivity().getString(R.string.share_dialog_item_sms));
        return values;
    }

    private ArrayAdapter<String> getAdapter(ArrayList<String> values) {
        return new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_singlechoice,
                values);
    }

}
