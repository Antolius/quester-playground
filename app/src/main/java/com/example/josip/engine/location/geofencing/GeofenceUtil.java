package com.example.josip.engine.location.geofencing;

import com.example.josip.model.Checkpoint;
import com.google.android.gms.location.Geofence;

public class GeofenceUtil {

    public static Geofence fromCheckpoint(Checkpoint checkpoint) {

        return new Geofence.Builder()
                .setRequestId(String.valueOf(checkpoint.getId()))
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setLoiteringDelay(1000)
                .setCircularRegion(
                        checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                        checkpoint.getArea().aproximatingCircle().getCenter().getLatitude(),
                        (float) checkpoint.getArea().aproximatingCircle().getRadius())
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }
}
