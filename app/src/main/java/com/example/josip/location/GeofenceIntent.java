package com.example.josip.location;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import java.util.ArrayList;
import java.util.List;

public class GeofenceIntent extends IntentService {

    public GeofenceIntent() {
        super("geofence intent");
    }

    public GeofenceIntent(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ArrayList<Checkpoint> checkpoints =
                (ArrayList<Checkpoint>) intent.getSerializableExtra("checkpoints");
        Checkpoint checkpoint =
                getTriggeringCheckpoint(checkpoints, LocationClient.getTriggeringGeofences(intent));

        Location location = LocationClient.getTriggeringLocation(intent);

        if (checkpoint.getArea().isInside(Point.fromLocation(location))){
            sendBroadcast(new Intent("Entered checkpoint area")
                                .putExtra("checkpoint", checkpoint));
        }
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
