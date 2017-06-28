package com.infinitystudios.foss_mec;

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
    public Event(int _id, String title, String description, String ImageURL, String type) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.ImageURL = ImageURL;
        this.type = type;
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
}
