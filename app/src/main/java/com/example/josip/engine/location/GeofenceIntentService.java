package com.example.josip.engine.location;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

public class GeofenceIntentService extends IntentService {

    public GeofenceIntentService() {
        super("GeofenceIntentService");
    }

    public GeofenceIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("QUESTER", "Geofence triggered");

        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        List<Geofence> fences = event.getTriggeringGeofences();
        Location location = event.getTriggeringLocation();

        ArrayList<String> checkpointIds = intent.getStringArrayListExtra(LocationProcessor.CHECKPOINTS_ARRAY_LIST_EXTRA_ID);

        String checkpointId = getTriggeringCheckpoint(checkpointIds, fences);

        sendBroadcast(new Intent("Entered checkpoint area")
                .putExtra(LocationProcessor.CHECKPOINT_EXTRA_ID, checkpointId)
                .putExtra(LocationProcessor.TRIGGERING_LOCATION, location)
        );
    }

    private String getTriggeringCheckpoint(List<String> checkpointIds,
                                               List<Geofence> triggerList) {

        //TODO; handle overlaps
        for (Geofence geofence : triggerList) {
            for (String checkpointId : checkpointIds) {
                if (checkpointId.equals(geofence.getRequestId())) {
                    return checkpointId;
                }
            }
        }
        throw new RuntimeException("Checkpoint error");
    }
}

