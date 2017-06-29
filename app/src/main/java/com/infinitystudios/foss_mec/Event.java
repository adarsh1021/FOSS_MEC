package com.infinitystudios.foss_mec;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adarsh on 28-06-2017.
 */

public class Event {
    private int _id;
    private String title;
    private String description;
    private String ImageURL;
    private String type;
    private int status;
    public Event(int _id, String title, String description, String ImageURL, String type, int status) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.ImageURL = ImageURL;
        this.type = type;
        this.status = status;
    }

    public static ArrayList<Event> getEventsFromDB(Context context, String type){
        DBHandler dbHandler = new DBHandler(context, null, null, 1);
        // db query events
        String dbString = dbHandler.databaseToString(type);
        ArrayList<Event> events = new ArrayList<Event>(); // Array list of events
        if (dbString != "") {
            String[] t = dbString.split(";");
            for (int i = 0; i < t.length; i++) {
                String[] u = t[i].split(",");
                Event event = new Event(Integer.parseInt(u[0]), u[1], u[2], u[3], u[4], Integer.parseInt(u[5]));
                events.add(event);
            }
        }
        return events;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public String getType() {
        return type;
    }

    public int get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
