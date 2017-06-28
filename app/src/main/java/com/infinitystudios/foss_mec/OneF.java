package com.infinitystudios.foss_mec;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
    private Event event;
    private ArrayList<Event> events;
    private DBHandler dbHandler;

    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_one, container, false);
        listView = (ListView) rootView.findViewById(R.id.activities_list);
        dbHandler = new DBHandler(getContext(), null, null, 1);
        // db query events
        String dbString = dbHandler.databaseToString("A");
        events = new ArrayList<Event>(); // Array list of events
        if (dbString != "") {
            String[] t = dbString.split(";");
            for (int i = 0; i < t.length; i++) {
                String[] u = t[i].split(",");
                event = new Event(Integer.parseInt(u[0]), u[1], u[2], u[3], u[4]);
                events.add(event);
            }
        }


        final EventsAdapter adapter = new EventsAdapter(getContext(), R.layout.event_layout, events);
        listView.setAdapter(adapter);

        return rootView;
    }

}
