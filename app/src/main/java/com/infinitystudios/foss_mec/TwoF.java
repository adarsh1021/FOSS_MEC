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
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_two, container, false);
        listView = (ListView) rootView.findViewById(R.id.activities_list);

        final EventsAdapter adapter = new EventsAdapter(getContext(), R.layout.event_layout, Event.getEventsFromDB(getContext(),"E"));
        listView.setAdapter(adapter);

        return rootView;

    }
}