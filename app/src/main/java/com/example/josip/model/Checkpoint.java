package com.example.josip.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.FileDescriptor;
import java.io.Serializable;

/**
 * Created by Josip on 10/08/2014.
 */
public final class Checkpoint implements Parcelable{

    private long id;
    private String name;
    private CheckpointArea area;
    private File viewHtml;
    private File eventsScript;

    public static Creator<Checkpoint> CREATOR = new Creator<Checkpoint>() {
        @Override
        public Checkpoint createFromParcel(Parcel source) {
            return new Checkpoint(source);
        }

        @Override
        public Checkpoint[] newArray(int size) {
            return new Checkpoint[size];
        }
    };

    public Checkpoint() {
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeLong(getId());
        destination.writeString(getName());
        destination.writeString(getViewHtml().getAbsolutePath());
        destination.writeString(getEventsScript().getAbsolutePath());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Checkpoint)) return false;

        Checkpoint that = (Checkpoint) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 47));
    }

    private Checkpoint(Parcel source) {
        this.id = source.readLong();
        this.name = source.readString();
        this.viewHtml = new File(source.readString());
        this.eventsScript = new File(source.readString());
    }
}
