package com.example.josip.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Josip on 10/08/2014.
 */
public final class Checkpoint implements Serializable {

    private long id;
    private String name;
    private CheckpointArea area;
    private File viewHtml;
    private File eventsScript;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CheckpointArea getArea() {
        return area;
    }

    public void setArea(CheckpointArea area) {
        this.area = area;
    }

    public File getViewHtml() {
        return viewHtml;
    }

    public void setViewHtml(File viewHtml) {
        this.viewHtml = viewHtml;
    }

    public File getEventsScript() {
        return eventsScript;
    }

    public void setEventsScript(File eventsScript) {
        this.eventsScript = eventsScript;
    }

}
