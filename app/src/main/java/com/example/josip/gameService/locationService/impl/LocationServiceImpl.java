package com.example.josip.gameService.locationService.impl;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.josip.gameService.locationService.LocationService;
import com.example.josip.location.GeofenceIntent;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import static com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import static com.google.android.gms.location.LocationClient.*;

/**
 * Created by tdubravcevic on 10.8.2014!
 */
public class LocationServiceImpl implements LocationService,
        ConnectionCallbacks,
        OnConnectionFailedListener,
        LocationListener,
        OnAddGeofencesResultListener{

    private LocationClient locationClient;

    public LocationServiceImpl(Context context, ArrayList<Checkpoint> checkpointList) {

        locationClient = new LocationClient(context, this, this);

        //move to on connected
        List<Geofence> geofences = new ArrayList<Geofence>();
        for(Checkpoint checkpoint : checkpointList) {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(checkpoint.getId()+"")
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .setCircularRegion(checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                            checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                            checkpoint.getArea().aproximatingCircle().getRadius())
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build();
            geofences.add(geofence);
        }

        Intent intent = new Intent(context, GeofenceIntent.class);
        intent.putParcelableArrayListExtra("checkpoints", checkpointList);
        locationClient.addGeofences(geofences, PendingIntent.getService(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT), this);
    }

    public void start() {
        locationClient.connect();
    }

    public void stop() {
        locationClient.disconnect();
    }

    public Point getCurrentLocation() {
        Location location = locationClient.getLastLocation();
        return new Point(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("QUESTER-CONN", "Is connected");
        locationClient.requestLocationUpdates(
                LocationRequest.create().setInterval(5000), this);
    }

    @Override
    public void onDisconnected() {
        Log.d("QUESTER-CONN", "Is disconnected");
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
    public void onAddGeofencesResult(int statusCode, String[] strings) {
        if (LocationStatusCodes.SUCCESS == statusCode) {
            Log.d("QUESTER", "Added geofences");
        } else {
            Log.e("QUESTE", "Adding geofences failed");
        }
    }
}
