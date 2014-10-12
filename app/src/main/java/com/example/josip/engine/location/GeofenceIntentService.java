package com.example.josip.engine.location;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import java.util.ArrayList;
import java.util.List;

public class GeofenceIntentService extends IntentService {

    public GeofenceIntentService() {
        super("geofence intent");
    }

    public GeofenceIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ArrayList<Checkpoint> checkpoints = intent.getParcelableArrayListExtra(LocationProcessor.CHECKPOINTS_ARRAY_LIST_EXTRA_ID);
        Checkpoint checkpoint = getTriggeringCheckpoint(checkpoints, LocationClient.getTriggeringGeofences(intent));

        Location location = LocationClient.getTriggeringLocation(intent);

        if (checkpoint.getArea().isInside(Point.fromLocation(location))) {
            sendBroadcast(new Intent("Entered checkpoint area").putExtra(LocationProcessor.CHECKPOINT_EXTRA_ID, checkpoint));
        }
    }

    private Checkpoint getTriggeringCheckpoint(List<Checkpoint> checkpoints,
                                               List<Geofence> triggerList) {

        //TODO; handle overlaps
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

