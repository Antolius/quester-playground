package com.example.josip.gameService.locationService.impl;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.josip.gameService.locationService.LocationService;
import com.example.josip.location.GeofenceIntent;
import com.example.josip.model.Checkpoint;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationStatusCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import static com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import static com.google.android.gms.location.LocationClient.*;

/**
 * Created by tdubravcevic on 10.8.2014!
 */
public class LocationServiceImpl implements LocationService,
        ConnectionCallbacks,
        OnConnectionFailedListener,
        OnAddGeofencesResultListener,
        OnRemoveGeofencesResultListener {

    private LocationClient locationClient;
    private Context context;

    public LocationServiceImpl(Context context) {

        this.locationClient = new LocationClient(context, this, this);
        this.context = context;
    }

    @Override
    public void start() {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Location service started");
        }

        locationClient.connect();
    }

    @Override
    public void stop() {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Location service stopped");
        }

        locationClient.disconnect();
    }

    @Override
    public void registerCheckpointAreas(Set<Checkpoint> checkpoints) {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Registering checkpoint areas");
        }

        List<Geofence> geofences = new ArrayList<Geofence>();
        for (Checkpoint checkpoint : checkpoints) {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(checkpoint.getId() + "")
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .setCircularRegion(checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                            checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                            checkpoint.getArea().aproximatingCircle().getRadius())
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build();
            geofences.add(geofence);
        }

        ArrayList arrayList = new ArrayList<Checkpoint>();
        arrayList.addAll(checkpoints);
        Intent intent = new Intent(context, GeofenceIntent.class).putExtra("checkpoints", arrayList);
        locationClient.addGeofences(geofences,
                PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT), this);
    }

    @Override
    public void unregisterCheckpointArea(Checkpoint checkpoint) {
        locationClient.removeGeofences(Arrays.asList(checkpoint.getId()+""), this);
    }


    @Override
    public void onConnected(Bundle bundle) {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Location service client connected");
        }
    }

    @Override
    public void onDisconnected() {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Location service client disconnected");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (Log.isLoggable("QUESTER", Log.WARN)) {
            Log.w("QUESTER", "Location service client connection failed");
        }
    }

    @Override
    public void onAddGeofencesResult(int statusCode, String[] strings) {

        if (LocationStatusCodes.SUCCESS == statusCode) {
            if (Log.isLoggable("QUESTER", Log.DEBUG)) {
                Log.d("QUESTER", "Location service, adding geofences succeeded");
            }
        } else {
            if (Log.isLoggable("QUESTER", Log.WARN)) {
                Log.w("QUESTER", "Location service, adding geofences failed");
            }
        }
    }

    @Override
    public void onRemoveGeofencesByRequestIdsResult(int status, String[] strings) {
        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Location service, removing geofence");
        }
    }

    @Override
    public void onRemoveGeofencesByPendingIntentResult(int status, PendingIntent pendingIntent) {
        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Location service, removing geofence");
        }
    }
}
