package com.example.josip.engine.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.util.Log;

import com.example.josip.Logger;
import com.example.josip.engine.location.geofencing.GeofencingClient;
import com.example.josip.engine.location.geofencing.GoogleGeofencingClient;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.google.android.gms.location.Geofence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LocationProcessor extends BroadcastReceiver {

    public static final Logger logger = Logger.getLogger(LocationProcessor.class);

    public static final String CHECKPOINT_EXTRA_ID = "CHECKPOINT";
    public static final String CHECKPOINTS_ARRAY_LIST_EXTRA_ID = "CHECKPOINTS";
    public static final String TRIGGERING_LOCATION = "LOCATION";

    private Context context;
    private GeofencingClient geofencingClient;

    private LocationReachedCallback locationReachedCallback;

    private Set<Checkpoint> currentCheckpoints;

    public LocationProcessor(Context context, GeofencingClient geofencingClient, LocationReachedCallback locationReachedCallback) {
        this.context = context;
        this.geofencingClient = geofencingClient;
        this.locationReachedCallback = locationReachedCallback;
    }

    public LocationProcessor(Context context, LocationReachedCallback locationReachedCallback) {
        this.context = context;
        this.locationReachedCallback = locationReachedCallback;

        geofencingClient = new GoogleGeofencingClient(context) {
            @Override
            public void onStarted() {
                trackLocation(currentCheckpoints);
            }
        };

    }

    public void start(Set<Checkpoint> rootCheckpoints) {

        logger.debug("Location service started");

        this.currentCheckpoints = rootCheckpoints;

        context.registerReceiver(this, new IntentFilter(GeofenceIntentService.GEOFENCE_INTENT_NAME));

        geofencingClient.start();
    }

    public void stop() {

        logger.debug("Location service stopped");

        geofencingClient.stop();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Checkpoint triggeringCheckpoint
                = getTriggeringCheckpoint(intent.getStringExtra(CHECKPOINT_EXTRA_ID));
        Location triggeringLocation
                = intent.getParcelableExtra(LocationProcessor.TRIGGERING_LOCATION);

        if (triggeringCheckpoint.getArea().isInside(Point.fromLocation(triggeringLocation))) {

            locationReachedCallback.locationReached(triggeringCheckpoint);
        }
    }

    private Checkpoint getTriggeringCheckpoint(String triggeringCheckpointId) {

        for (Checkpoint checkpoint : currentCheckpoints) {
            if (checkpoint.getId() == Long.valueOf(triggeringCheckpointId)) {
                return checkpoint;
            }
        }
        throw new RuntimeException("Checkpoint with " + triggeringCheckpointId + "not found");
    }

    public void trackLocation(Set<Checkpoint> checkpoints) {

        logger.debug("Registering checkpoint areas");

        this.currentCheckpoints = checkpoints;

        geofencingClient.registerGeofences(buildGeofences(checkpoints));
        //remove old geofences

    }

    private List<Geofence> buildGeofences(Set<Checkpoint> checkpoints) {
        List<Geofence> geofences = new ArrayList<Geofence>();

        for (Checkpoint checkpoint : checkpoints) {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(String.valueOf(checkpoint.getId()))
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .setLoiteringDelay(1000)
                    .setCircularRegion(
                            checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                            checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                            (float) checkpoint.getArea().aproximatingCircle().getRadius())
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build();
            geofences.add(geofence);
        }
        return geofences;
    }

}
