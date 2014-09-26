package com.example.josip.location;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import static com.google.android.gms.common.GooglePlayServicesClient.*;

public class SendMockLocationsService extends Service implements
        ConnectionCallbacks, OnConnectionFailedListener {


    private LocationClient locationClient;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION, 0) == 0)
            Toast.makeText(this, "U jebote nije ukljucen mock", Toast.LENGTH_LONG).show();

        locationClient = new LocationClient(this,this,this);
        locationClient.connect();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        locationClient.setMockMode(true);
        Location location = new Location("flp");
        location.setLatitude(1.0);
        location.setLongitude(1.0);
        location.setAccuracy(3.0f);
        location.setTime(System.currentTimeMillis());
        locationClient.setMockLocation(location);
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
