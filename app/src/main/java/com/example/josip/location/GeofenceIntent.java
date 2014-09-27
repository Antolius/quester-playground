package com.example.josip.location;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.josip.gameService.engine.GameEngine;
import com.example.josip.jstest.MyApplication;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import java.util.List;

public class GeofenceIntent extends IntentService {

    GameEngine gameEngine;

    public GeofenceIntent() {
        super("test");
    }

    public GeofenceIntent(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int transitionType = LocationClient.getGeofenceTransition(intent);

        List<Checkpoint> checkpoints = intent.getParcelableArrayListExtra("checkpoints");
        //Checkpoint checkpoint = getTriggeringCheckpoint(checkpoints, LocationClient.getTriggeringGeofences(intent));

        Location location = LocationClient.getTriggeringLocation(intent);

        Intent broadcastIntent = new Intent("Entered checkpoint area");
        sendBroadcast(broadcastIntent);
        //if (checkpoint.getArea().isInside(Point.fromLocation(location)))
            //gameEngine.onCheckpointAreaEnter(checkpoint);

    }

    private Checkpoint getTriggeringCheckpoint(List<Checkpoint> checkpoints,
                                               List<Geofence> triggerList) {

        for (Geofence geofence : triggerList) {
            for (Checkpoint checkpoint : checkpoints) {
                if (checkpoint.getId() == Long.getLong(geofence.getRequestId())) {
                    return checkpoint;
                }
            }
        }
        throw new RuntimeException("Checkpoint error");
    }
}
