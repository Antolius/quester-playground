package com.example.josip.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.josip.engine.GameEngine;
import com.example.josip.gameService.GameEngineService;
import com.example.josip.jstest.R;
import com.example.josip.model.Quest;
import com.example.josip.providers.QuestProvider;
import com.google.android.gms.location.LocationClient;

public class LocationTestActivity extends Activity {

    private LocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_test);

        Intent sendMockLocationServiceIntent = new Intent(this, SendMockLocationService.class);
        startService(sendMockLocationServiceIntent);

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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
