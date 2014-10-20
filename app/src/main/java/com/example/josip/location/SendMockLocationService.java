package com.example.josip.location;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.google.android.gms.common.GooglePlayServicesClient.*;

/**
 * Created by tdubravcevic on 18.10.2014!
 */
public class SendMockLocationService extends Service implements
        ConnectionCallbacks, OnConnectionFailedListener {

    public List<Location> getMockedLocations(){
        List<Location> mockLocations = new ArrayList<Location>();

        for(int i = 0; i<5; i++){
            Location mockLocation = new Location("flp");
            mockLocation.setAccuracy(3.0f);
            mockLocation.setLatitude(50.0+randomDouble());
            mockLocation.setLongitude(50.0+randomDouble());
            mockLocation.setAccuracy(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mockLocations.add(mockLocation);
        }


        for(int i = 0; i<100; i++){
            Location mockLocation = new Location("flp");
            mockLocation.setAccuracy(3.0f);
            mockLocation.setLatitude(1.0+randomDouble());
            mockLocation.setLongitude(1.0+randomDouble());
            mockLocation.setAccuracy(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mockLocations.add(mockLocation);
        }
        return mockLocations;
    }


    private GoogleApiClient locationClient;
    Random random;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, 0) == 0)
            Toast.makeText(this, "Setting for mock locations not enabled!", Toast.LENGTH_LONG).show();

        locationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        Log.d("QUESTER", "mock client connected");

                        LocationServices.FusedLocationApi.setMockMode(locationClient, true);

                        for (Location mockLocation : getMockedLocations()) {

                            mockLocation.setTime(System.currentTimeMillis());

                            LocationServices.FusedLocationApi.setMockLocation(locationClient, mockLocation);
                            Log.d("QUESTER", "Setting mock location" + mockLocation);

                            try {
                                Thread.sleep((long) (1000));
                            } catch (InterruptedException e) {
                                return ;
                            }
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d("QUESTER", "mock client connection suspended");
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.d("QUESTER", "mock client connection failed");
                    }
                })
                .build();


        locationClient.connect();

        random = new Random();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {}

    private double randomDouble(){
        return 0.01 + (0.02 - 0.01) * random.nextDouble();
    }


    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
