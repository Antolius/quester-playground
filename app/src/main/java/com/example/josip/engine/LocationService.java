package com.example.josip.engine;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import java.util.List;

/**
 * Created by tdubravcevic on 10.8.2014!
 */
public class LocationService implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener{

    private LocationClient locationClient;

    public LocationService(Context context, List<Checkpoint> checkpointList) {
        locationClient = new LocationClient(context, this, this);

        /*
        for(Checkpoint checkpoint : checkpointList) {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId("1")
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .setCircularRegion(1,1,1)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build();
        }
        */

        //locationClient.addGeofences(null,);

    }

    public void start(){
        locationClient.connect();
    }

    public void stop(){
        locationClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("QUESTER-CONN", "Is connected");
        Log.d("QUESTER", getCurrentLocation().getLatitude()+"");
    }

    @Override
    public void onDisconnected() {
        Log.d("QUESTER-CONN", "Is dsiconnected");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("QUESTER-CONN", "Connecting failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("QUESTER", "Location changed" + location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public Point getCurrentLocation(){
        Location location = locationClient.getLastLocation();
        return new Point(location.getLatitude(), location.getLongitude());
    }
}
