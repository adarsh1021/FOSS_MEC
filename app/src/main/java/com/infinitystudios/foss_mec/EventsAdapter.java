package com.infinitystudios.foss_mec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adarsh on 28-06-2017.
 */

public class EventsAdapter extends ArrayAdapter<Event> {
    Context context;
    View v;
    private ArrayList<Event> events;
    public EventsAdapter(Context context, int resource, ArrayList<Event> events) {
        super(context, resource, events);
        this.context = context;
        this.events = events;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        v = convertView;
        if (v==null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.event_layout, null);
        }
        Event e = events.get(position);
        if (e!=null) {
            TextView tvTitle = (TextView) v.findViewById(R.id.tvEventTitle);
            TextView tvDescription = (TextView) v.findViewById(R.id.tvEventDescription);

            tvTitle.setText(String.valueOf(e.getTitle()));
            tvDescription.setText(String.valueOf(e.getDescription()));
        }
        return v;
    }


}
