package com.example.josip.engine.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcel;

import com.example.josip.engine.location.geofencing.GeofenceUtil;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.example.josip.model.area.Circle;
import com.example.josip.model.area.CircleArea;
import com.google.android.gms.location.Geofence;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class GeofenceIntentServiceTest {

    private GeofenceIntentService geofenceIntentService;

    private ArrayList<Parcel> geofences = new ArrayList<Parcel>();

    public Parcel writeToParcel(Geofence geofence){

        Parcel parcel = Parcel.obtain();
        parcel.writeString(geofence.getRequestId());
        return parcel;
    }

    @Ignore
    @Test
    public void test(){

        ArrayList<String> ids = new ArrayList<String>();
        ids.add("1");

        Intent intent = new Intent();
        intent.putStringArrayListExtra(LocationProcessor.REGISTERED_CHECKPOINTS_IDS, ids);

        givenCheckpoint(1L, new Point(1.0,1.0), 1000.0);

        intent.putExtra("com.google.android.location.intent.extra.geofence_list", geofences);
        Location location = new Location("fused");
        location.setLatitude(1.0);
        location.setLongitude(1.0);
        intent.putExtra("com.google.android.location.intent.extra.triggering_location", location);
        intent.putExtra("com.google.android.location.intent.extra.transition", 1);

        geofenceIntentService = new GeofenceIntentService();

        geofenceIntentService.onHandleIntent(intent);
    }

    private void givenCheckpoint(Long id, Point center, Double radius) {

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setId(id);
        Circle circle = new Circle();
        circle.setCenter(center);
        circle.setRadius(radius);
        checkpoint.setArea(new CircleArea(circle));

        this.geofences.add(writeToParcel(GeofenceUtil.fromCheckpoint(checkpoint)));
    }
}
