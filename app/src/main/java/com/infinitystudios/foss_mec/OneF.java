package com.infinitystudios.foss_mec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class OneF extends Fragment {

    private ListView listView;
    private ArrayList<Event> events;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_one, container, false);

        listView = (ListView) rootView.findViewById(R.id.activities_list);
        events = new ArrayList<Event>();

        Event e1 = new Event(0,"EVENT 1", "Description line 1\nDescription line 2\nline 3");
        Event e2 = new Event(1,"EVENT 2", "Description line 1\nDescription line 2\nline 3");
        events.add(e1);
        events.add(e2);

        final EventsAdapter adapter = new EventsAdapter(getActivity(), R.layout.event_layout, events);
        listView.setAdapter(adapter);

        return rootView;
    }


}
