package com.example.josip.engine.location.geofencing;

import com.google.android.gms.location.Geofence;

import java.util.List;

public interface GeofencingClient {

    public void start();

    public void stop();

    public void registerGeofences(List<Geofence> geofenceList);

    public void onStarted();
}
