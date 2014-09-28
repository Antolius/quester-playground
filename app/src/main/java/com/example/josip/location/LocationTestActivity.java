package com.example.josip.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.josip.gameService.GameEngineService;
import com.example.josip.gameService.engine.GameEngine;
import com.example.josip.jstest.R;
import com.example.josip.providers.QuestProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.GooglePlayServicesClient.*;
import static com.google.android.gms.location.LocationClient.*;

public class LocationTestActivity extends Activity {

    private LocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_test);

        Intent sendMockLocationServiceIntent = new Intent(this, SendMockLocationsService.class);
        startService(sendMockLocationServiceIntent);

        Intent gameEngineServiceIntent = new Intent(this, GameEngineService.class)
                .putExtra("quest", QuestProvider.getMockedQuests(1).get(0));
        startService(gameEngineServiceIntent);
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
}
