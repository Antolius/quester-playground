package com.example.josip.engine.location;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LocationProcessor extends BroadcastReceiver{

    public static final String CHECKPOINT_EXTRA_ID = "CHECKPOINT";
    public static final String CHECKPOINTS_ARRAY_LIST_EXTRA_ID = "CHECKPOINTS";
    public static final String TRIGGERING_LOCATION = "LOCATION";

    private Context context;
    private GoogleApiClient apiClient;

    private LocationReachedCallback locationReachedCallback;

    private Set<Checkpoint> currentCheckpoints;

    public LocationProcessor(Context context, LocationReachedCallback locationReachedCallback) {
        this.context = context;
        this.locationReachedCallback = locationReachedCallback;

        apiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

                    @Override
                    public void onConnected(Bundle bundle) {
                        Log.d("QUESTER", "client connected");
                        //LocationServices.FusedLocationApi.setMockMode(apiClient, false);
                        trackLocation(currentCheckpoints);
                        startLocationRequests();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d("QUESTER", "client connection suspended");
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.d("QUESTER", "client connection failed");
                    }
                })
                .build();

        context.registerReceiver(this, new IntentFilter("Entered checkpoint area"));
    }

    private void startLocationRequests() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                apiClient,
                LocationRequest.create()
                        .setInterval(5000)
                        .setFastestInterval(1000)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.d("QUESTER", "Mock location received" + location.toString());
                    }
                }
        );
    }

    public void start(Set<Checkpoint> rootCheckpoints) {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Location service started");
        }

        this.currentCheckpoints = rootCheckpoints;

        apiClient.connect();
    }

    public void stop() {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Location service stopped");
        }

        apiClient.disconnect();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String triggeringCheckpointId = intent.getStringExtra(CHECKPOINT_EXTRA_ID);
        Location location = intent.getParcelableExtra(LocationProcessor.TRIGGERING_LOCATION);

        Checkpoint triggeringCheckpoint = null;
        for(Checkpoint checkpoint : currentCheckpoints){
            if(checkpoint.getId() == Long.valueOf(triggeringCheckpointId)){
                triggeringCheckpoint = checkpoint;
            }
        }
        if(triggeringCheckpoint == null){
            return;
        }

        if (triggeringCheckpoint.getArea().isInside(Point.fromLocation(location))) {

            locationReachedCallback.locationReached(triggeringCheckpoint);
        }
    }

    public void trackLocation(Set<Checkpoint> checkpoints) {

        this.currentCheckpoints = checkpoints;

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Registering checkpoint areas");
        }

        ArrayList<String> checkpointIds = new ArrayList<String>();
        for(Checkpoint checkpoint : checkpoints){
            checkpointIds.add(String.valueOf(checkpoint.getId()));
        }

        //remove old geofences

        LocationServices.GeofencingApi.addGeofences(
                apiClient,
                buildGeofences(checkpoints),
                PendingIntent.getService(
                        context,
                        0,
                        new Intent(context, GeofenceIntentService.class)
                                .putStringArrayListExtra(CHECKPOINTS_ARRAY_LIST_EXTRA_ID, checkpointIds),
                                //.putParcelableArrayListExtra(CHECKPOINTS_ARRAY_LIST_EXTRA_ID, new ArrayList<Checkpoint>(checkpoints)),
                        PendingIntent.FLAG_UPDATE_CURRENT
                )
        );
    }

    private List<Geofence> buildGeofences(Set<Checkpoint> checkpoints) {
        List<Geofence> geofences = new ArrayList<Geofence>();

        for (Checkpoint checkpoint : checkpoints) {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(String.valueOf(checkpoint.getId()))
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .setLoiteringDelay(1000)
                    .setCircularRegion(checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                            checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                            (float) checkpoint.getArea().aproximatingCircle().getRadius())
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build();
            geofences.add(geofence);
        }
        return geofences;
    }

}
