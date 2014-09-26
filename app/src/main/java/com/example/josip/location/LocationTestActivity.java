package com.example.josip.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.josip.jstest.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.android.gms.common.GooglePlayServicesClient.*;
import static com.google.android.gms.location.LocationClient.*;

public class LocationTestActivity extends Activity implements
        ConnectionCallbacks, OnConnectionFailedListener,
        LocationListener, OnAddGeofencesResultListener {

    private LocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_test);

        locationClient = new LocationClient(this, this, this);
        locationClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        Intent intent = new Intent(this, SendMockLocationsService.class);
        startService(intent);

        locationClient.requestLocationUpdates(LocationRequest.create().setInterval(1000), this);

        List<Geofence> geofences = new ArrayList<Geofence>();
        for (Location location : SendMockLocationsService.getMockedLocations()) {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(location.getLatitude() + "")
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .setCircularRegion(location.getLatitude(), location.getLongitude(), 1000.0f)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build();
            geofences.add(geofence);
        }

        Intent geofenceIntent = new Intent(this, GeofenceIntent.class);

        locationClient.addGeofences(geofences, PendingIntent.getService(
                this,
                0,
                geofenceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT), this);

    }

    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(this, location.getLatitude() + "|" + location.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onAddGeofencesResult(int statusCode, String[] strings) {
        if (LocationStatusCodes.SUCCESS == statusCode) {
            Log.d("QUESTER", "Added geofences");
        } else {
            Log.e("QUESTE", "Adding geofences failed");
        }
    }
}
