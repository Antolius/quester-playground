package com.example.josip.geometry;

import com.google.android.gms.location.Geofence;

/**
 * Created by tdubravcevic on 15.9.2014!
 */
public class Geometry {

    public void test(){
        new Geofence.Builder().setCircularRegion(1,1,1).build();
    }
}
