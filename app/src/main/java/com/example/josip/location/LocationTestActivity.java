package com.example.josip.location;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.josip.jstest.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import static com.google.android.gms.common.GooglePlayServicesClient.*;

public class LocationTestActivity extends Activity implements
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener{

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
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, "L: " + location.getProvider(), Toast.LENGTH_SHORT).show();
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
}
