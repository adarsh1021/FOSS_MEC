package com.infinitystudios.foss_mec;

/**
 * Created by Adarsh on 28-06-2017.
 */

public class Event {
    private int _id;
    private String title;
    private String description;

    public Event(int _id, String title, String description) {
        this._id = _id;
        this.title = title;
        this.description = description;
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

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
