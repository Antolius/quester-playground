package com.example.josip.location;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.google.android.gms.common.GooglePlayServicesClient.*;

/**
 * Created by tdubravcevic on 18.10.2014!
 */
public class SendMockLocationService extends Service implements
        ConnectionCallbacks, OnConnectionFailedListener {

    public static List<Location> getMockedLocations(){
        List<Location> mockLocations = new ArrayList<Location>();

        for(int i = 0; i<20; i++){
            Location mockLocation = new Location("flp");
            mockLocation.setAccuracy(3.0f);
            mockLocation.setLatitude(i);
            mockLocation.setLongitude(i);
            mockLocations.add(mockLocation);
        }
        return mockLocations;
    }


    private LocationClient locationClient;
    Random random;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, 0) == 0)
            Toast.makeText(this, "Setting for mock locations not enabled!", Toast.LENGTH_LONG).show();

        locationClient = new LocationClient(this,this,this);
        locationClient.connect();

        random = new Random();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {

        locationClient.setMockMode(true);

        for (Location mockLocation : getMockedLocations()) {

            mockLocation.setTime(System.currentTimeMillis());

            locationClient.setMockLocation(mockLocation);

            try {
                Thread.sleep((long) (1000));
            } catch (InterruptedException e) {
                return ;
            }
        }
    }

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
