package com.infinitystudios.foss_mec;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Adarsh on 27-06-2017.
 */

public class TwoF extends Fragment {

    private ListView listView;
    private Event event;
    private ArrayList<Event> events;
    private DBHandler dbHandler;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_two, container, false);
        listView = (ListView) rootView.findViewById(R.id.activities_list);
        dbHandler = new DBHandler(getContext(), null, null, 1);
        // db query events
        String dbString = dbHandler.databaseToString("E");
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