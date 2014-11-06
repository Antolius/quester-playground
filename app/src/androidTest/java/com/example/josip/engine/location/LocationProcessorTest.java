package com.example.josip.engine.location;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.location.Location;

import com.example.josip.engine.location.geofencing.GeofencingClient;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.Point;
import com.example.josip.model.area.Circle;
import com.example.josip.model.area.CircleArea;
import com.google.android.gms.location.Geofence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class LocationProcessorTest {

    private LocationProcessor locationProcessor;
    private LocationReachedCallback callback = mock(LocationReachedCallback.class);
    private GeofencingClient geofencingClient = mock(GeofencingClient.class);

    private Set<Checkpoint> checkpoints;
    private Location triggeringLocation;
    private Long triggeringCheckpointId;

    @Before
    public void setUp() {
        checkpoints = new HashSet<Checkpoint>();
        locationProcessor = new LocationProcessor(Robolectric.application, geofencingClient, callback);
    }

    @Test
    public void geofencesClientStartedWhenLocationProcessorStarted() {

        whenStartLocationProcessor();

        thenGeofenceClientStarted();
    }

    @Test
    public void geofencesClientStoppedWhenLocationProcessorStopped() {

        whenStopLocationProcessor();

        thenGeofenceClientStopped();
    }

    @Test
    public void locationProcessorRegisteredAsBroadcastReceiverWhenStarted() {

        whenStartLocationProcessor();

        thenRegisteredAsBroadcastReceiver(LocationProcessor.class);
    }

    @Test
    public void geofencesRegisteredWhenTrackingLocations() {

        givenCheckpoint(1L, new Point(1.0,1.0), 1000.0);

        whenTrackLocations();

        thenGeofencesRegistered("1");
    }

    @Test
    public void locationReachedWhenGeofenceTriggered() {

        givenCheckpoint(1L, new Point(1.0,1.0), 1000.0);
        givenCheckpoint(2L, new Point(20.0,20.0), 1.0);
        givenTriggeringCheckpointId(1L);
        givenTriggeringLocationCoordinates(1.1,0.9);

        whenTrackLocations();
        whenGeofenceTriggered();

        thenLocationReachedCallbackInvokedForCheckpoint(1L);
    }

    @Test
    public void locationNotReachedWhenGeofenceTriggeredButLocationNotInInnerArea() {

        givenCheckpointWithCorruptArea(1L, new Point(1.0,1.0), 10.0);
        givenCheckpoint(2L, new Point(20.0,20.0), 1.0);
        givenTriggeringCheckpointId(1L);
        givenTriggeringLocationCoordinates(2.0,2.0);

        whenTrackLocations();
        whenGeofenceTriggered();

        thenLocationReachedCallbackNotInvoked();
    }

    private void givenCheckpointWithCorruptArea(Long id, Point center, Double radius) {

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setId(id);
        Circle circle = new Circle();
        circle.setCenter(center);
        circle.setRadius(radius);
        checkpoint.setArea(new CircleArea(circle){
            @Override
            public boolean isInside(Point point) {
                return false;
            }
        });

        this.checkpoints.add(checkpoint);
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

    private void givenTriggeringLocationCoordinates(Double latitude, Double longitude){

        Location location = new Location("test");
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        this.triggeringLocation = location;
    }

    private void givenTriggeringCheckpointId(Long id) {

        this.triggeringCheckpointId = id;
    }

    private void whenTrackLocations(){

        locationProcessor.trackLocation(checkpoints);
    }

    private void whenStartLocationProcessor(){

        locationProcessor.start(checkpoints);
    }

    private void whenStopLocationProcessor(){

        locationProcessor.stop();
    }

    private void whenGeofenceTriggered(){

        Intent intent = new Intent();
        intent.putExtra(LocationProcessor.TRIGGERING_CHECKPOINT_ID, triggeringCheckpointId.toString());
        intent.putExtra(LocationProcessor.TRIGGERING_LOCATION, triggeringLocation);

        locationProcessor.onReceive(Robolectric.application, intent);
    }

    private void thenGeofencesRegistered(String id) {

        ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);
        verify(geofencingClient).registerGeofences(argument.capture());

        List<Geofence> geofences = argument.getValue();

        for(Geofence geofence : geofences){
            assertEquals(id, geofence.getRequestId());
        }
    }

    private void thenGeofenceClientStarted() {

        verify(geofencingClient).start();
    }

    private void thenGeofenceClientStopped() {

        verify(geofencingClient).stop();
    }

    private void thenRegisteredAsBroadcastReceiver(Class aClass) {

        BroadcastReceiver registeredReceiver = Robolectric.getShadowApplication().getRegisteredReceivers().get(0).getBroadcastReceiver();
        assertEquals(registeredReceiver.getClass(), aClass);
    }

    private void thenLocationReachedCallbackInvokedForCheckpoint(Long checkpointId){

        Checkpoint triggeringCheckpoint = null;

        for(Checkpoint checkpoint : checkpoints){
            if(checkpointId.equals(checkpoint.getId())){
                triggeringCheckpoint = checkpoint;
            }
        }

        verify(callback).locationReached(triggeringCheckpoint);
    }

    private void thenLocationReachedCallbackNotInvoked(){

        verify(callback, never()).locationReached(any(Checkpoint.class));
    }

}
