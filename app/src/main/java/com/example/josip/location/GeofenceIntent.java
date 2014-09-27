package com.example.josip.location;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.josip.gameService.engine.GameEngine;
import com.example.josip.model.Checkpoint;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import java.util.List;

public class GeofenceIntent extends IntentService {

    GameEngine gameEngine;

    public GeofenceIntent(){
       super("test");
    }

    public GeofenceIntent(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int transitionType = LocationClient.getGeofenceTransition(intent);
        List<Geofence> triggerList = LocationClient.getTriggeringGeofences(intent);
        switch (transitionType){
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Log.d("QUESTER", "Dwelling at " + triggerList.get(0).getRequestId());
                break;
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Log.d("QUESTER", "Entered" + triggerList.get(0).getRequestId());
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Log.d("QUESTER", "Exited" + triggerList.get(0).getRequestId());
                break;
        }

        List<Checkpoint> checkpoints = intent.getParcelableArrayListExtra("checkpoints");
        for(Geofence geofence : triggerList){
            for(Checkpoint checkpoint : checkpoints){
                if(checkpoint.getId() == Long.getLong(geofence.getRequestId())){
                    gameEngine.onCheckpointAreaEnter(checkpoint);
                }
            }
        }
    }
}
