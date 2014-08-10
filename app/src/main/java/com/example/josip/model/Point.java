package com.example.josip.model;

/**
 * Created by Josip on 10/08/2014.
 */
public class Point {

    private double latiture;
    private double longitude;

    public Point() {
    }

    public Point(double latiture, double longitude) {
        this.latiture = latiture;
        this.longitude = longitude;
    }

    public double getLatiture() {
        return latiture;
    }

    public void setLatiture(double latiture) {
        this.latiture = latiture;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
