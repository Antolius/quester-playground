package com.example.josip.locationTest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.josip.engine.GameEngine;
import com.example.josip.jstest.R;

public class LocationTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_test);

        //Intent sendMockLocationServiceIntent = new Intent(this, SendMockLocationService.class);
        //startService(sendMockLocationServiceIntent);

        Intent gameEngineServiceIntent = new Intent(this, GameEngine.class);
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
