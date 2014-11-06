package com.example.josip.engine.location;

import android.app.IntentService;
import android.content.Intent;

import com.example.josip.Logger;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceIntentService extends IntentService {

    public static final Logger logger = Logger.getLogger(GeofenceIntentService.class);

    public static final String GEOFENCE_INTENT_NAME = "Entered checkpoint area";

    public GeofenceIntentService() {
        super("GeofenceIntentService");
    }

    public GeofenceIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        logger.debug("Geofence triggered");

        sendBroadcast(getGeofenceTriggeredIntent(
                intent.getStringArrayListExtra(LocationProcessor.REGISTERED_CHECKPOINTS_IDS),
                GeofencingEvent.fromIntent(intent)));
    }

    private Intent getGeofenceTriggeredIntent(List<String> checkpointIds, GeofencingEvent event) {

        return new Intent(GEOFENCE_INTENT_NAME)
                .putExtra(LocationProcessor.TRIGGERING_CHECKPOINT_ID,
                        getTriggeringCheckpointId(checkpointIds,event.getTriggeringGeofences()))
                .putExtra(LocationProcessor.TRIGGERING_LOCATION, event.getTriggeringLocation());
    }

    private String getTriggeringCheckpointId(List<String> checkpointIds, List<Geofence> triggerList) {

        for (Geofence geofence : triggerList) {

            int index = checkpointIds.indexOf(geofence.getRequestId());

            if (index != -1) {
                return checkpointIds.get(index);
            }
        }
        throw new RuntimeException("Checkpoint error");
    }
}

