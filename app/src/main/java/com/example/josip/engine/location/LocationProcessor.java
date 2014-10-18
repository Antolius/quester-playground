package com.example.josip.engine.location;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.example.josip.model.Checkpoint;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Josip on 12/10/2014!
 */
public class LocationProcessor extends BroadcastReceiver implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationClient.OnAddGeofencesResultListener,
        LocationClient.OnRemoveGeofencesResultListener {

    public static final String CHECKPOINT_EXTRA_ID = "CHECKPOINT";
    public static final String CHECKPOINTS_ARRAY_LIST_EXTRA_ID = "CHECKPOINTS";

    private Context context;
    private LocationClient locationClient;

    private LocationReachedCallback locationReachedCallback;

    private Set<Checkpoint> rootCheckpoints;

    public LocationProcessor(Context context, LocationReachedCallback locationReachedCallback) {
        this.context = context;
        this.locationReachedCallback = locationReachedCallback;

        this.locationClient = new LocationClient(context, this, this);
    }

    public void start(Set<Checkpoint> rootCheckpoints) {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Location service started");
        }

        this.rootCheckpoints = rootCheckpoints;
        locationClient.connect();
    }

    public void stop() {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Location service stopped");
        }

        locationClient.disconnect();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Checkpoint triggeringCheckpoint = intent.getParcelableExtra(CHECKPOINT_EXTRA_ID);
        locationReachedCallback.locationReached(triggeringCheckpoint);
    }


    public void trackLocation(Set<Checkpoint> checkpoints) {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Registering checkpoint areas");
        }

        locationClient.addGeofences(
                buildGeofences(checkpoints),
                PendingIntent.getService(
                        context,
                        0,
                        new Intent(context, GeofenceIntentService.class).putParcelableArrayListExtra(CHECKPOINTS_ARRAY_LIST_EXTRA_ID, new ArrayList<Checkpoint>(checkpoints)),
                        PendingIntent.FLAG_UPDATE_CURRENT),
                this
        );
    }

    private List<Geofence> buildGeofences(Set<Checkpoint> checkpoints) {
        List<Geofence> geofences = new ArrayList<Geofence>();

        for (Checkpoint checkpoint : checkpoints) {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(String.valueOf(checkpoint.getId()))
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .setCircularRegion(checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                            checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                            (float) checkpoint.getArea().aproximatingCircle().getRadius())
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build();
            geofences.add(geofence);
        }
        return geofences;
    }

    @Override
    public void onConnected(Bundle bundle) {

        trackLocation(rootCheckpoints);

        locationClient.requestLocationUpdates(
                LocationRequest.create()
                        .setInterval(5000)
                        .setFastestInterval(1000)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.d("QUESTER", location.toString());
                    }
                }
        );

    }

    @Override
    public void onDisconnected() {
        Log.d("QUESTER", "client disconnected");
    }

    @Override
    public void onAddGeofencesResult(int i, String[] strings) {
        Log.d("QUESTER", "geofences result " + strings);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("QUESTER", "connection failed");
    }

    @Override
    public void onRemoveGeofencesByRequestIdsResult(int i, String[] strings) {
        Log.d("QUESTER", "geofences removed");
    }

    @Override
    public void onRemoveGeofencesByPendingIntentResult(int i, PendingIntent pendingIntent) {
        Log.d("QUESTER", "geofences removed");
    }
}
