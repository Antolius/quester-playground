package com.example.josip.model;

import android.location.Location;

/**
 * Created by Josip on 10/08/2014.
 */
public class Point {

    private double latitude;
    private double longitude;

    public Point() {
    }

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Point fromLocation(Location location) {
        return new Point(location.getLatitude(), location.getLongitude());
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
