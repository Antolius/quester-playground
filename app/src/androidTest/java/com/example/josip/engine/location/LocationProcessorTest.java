package com.example.josip.engine.location;

import android.content.Intent;
import android.location.Location;

import com.example.josip.engine.location.geofencing.GeofencingClient;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.example.josip.model.area.CheckpointArea;
import com.example.josip.model.area.Circle;
import com.example.josip.model.area.CircleArea;
import com.example.josip.model.enums.MeasurementUnit;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class LocationProcessorTest {


    private LocationProcessor locationProcessor;
    private LocationReachedCallback callback = mock(LocationReachedCallback.class);
    private GeofencingClient geofencingClient = mock(GeofencingClient.class);

    private Set<Checkpoint> checkpoints;

    @Before
    public void setUp() {
        checkpoints = new HashSet<Checkpoint>();
        locationProcessor = new LocationProcessor(Robolectric.application, geofencingClient, callback);
    }

    @Test
    public void geofencesRegisteredWhenTrackingLocations() {

        givenCheckpoint(1L, new Point(1.0,1.0), 1000.0);

        whenTrackLocations();

        thenGeofencesRegistered("1");
    }

    private void givenCheckpoint(Long id, Point center, Double radius) {

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setId(id);
        Circle circle = new Circle();
        circle.setCenter(center);
        circle.setRadius(radius);
        checkpoint.setArea(new CircleArea(circle));

        this.checkpoints.add(checkpoint);
    }

    private void whenTrackLocations(){

        locationProcessor.trackLocation(checkpoints);
    }

    private void thenGeofencesRegistered(String id) {

        ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);
        verify(geofencingClient).registerGeofences(argument.capture());

        List<Geofence> geofences = argument.getValue();

        for(Geofence geofence : geofences){
            assertEquals(id, geofence.getRequestId());
        }
    }

    @Test
    public void test() {

        locationProcessor = new LocationProcessor(Robolectric.application, geofencingClient, callback);

        Intent intent = new Intent();
        intent.putExtra(LocationProcessor.CHECKPOINT_EXTRA_ID, "1");
        Location location = new Location("test");
        location.setLatitude(1.0);
        location.setLongitude(1.0);
        intent.putExtra(LocationProcessor.TRIGGERING_LOCATION, location);

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setId(1);
        checkpoint.setArea(new CheckpointArea() {
            @Override
            public boolean isInside(Point point) {
                return true;
            }

            @Override
            public double distanceFrom(Point point, MeasurementUnit messurmentUnit) {
                return 0;
            }

            @Override
            public Circle aproximatingCircle() {
                Circle circle = new Circle();
                circle.setCenter(new Point(1.0, 1.0));
                circle.setRadius(100);
                return circle;
            }

            @Override
            public JSONObject getJsonData() throws JSONException {
                return null;
            }
        });

        Set<Checkpoint> checkpoints = new HashSet<Checkpoint>();
        checkpoints.add(checkpoint);

        locationProcessor.trackLocation(checkpoints);

        verify(geofencingClient).registerGeofences(anyList());

        locationProcessor.onReceive(Robolectric.application, intent);

        verify(callback).locationReached(checkpoint);

    }
}
