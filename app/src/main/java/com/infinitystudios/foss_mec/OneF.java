package com.infinitystudios.foss_mec;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


// https://docs.google.com/spreadsheets/d/1rKzPTY-3_bUQzEfXPmpKRQfskLXeNOja1WDEDYoExfE/edit?usp=sharing
public class OneF extends Fragment {

    private ListView listView;
    private EventsAdapter adapter;

    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_one, container, false);

        listView = (ListView) rootView.findViewById(R.id.activities_list);

        adapter = new EventsAdapter(getContext(), R.layout.event_layout, Event.getEventsFromDB(getContext(), "A"));
        listView.setAdapter(adapter);

        return rootView;
    }

}
