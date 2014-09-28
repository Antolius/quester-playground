package com.example.josip.gameService;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.example.josip.gameService.engine.GameEngine;
import com.example.josip.gameService.locationService.LocationService;
import com.example.josip.gameService.locationService.impl.LocationServiceImpl;
import com.example.josip.gameService.stateProvider.GameStateProvider;
import com.example.josip.location.CheckpointAreaEnteredListener;

/**
 * Created by tdubravcevic on 10.8.2014!
 */
public class GameEngineService extends Service {

    private LocationService locationService;
    private GameStateProvider gameStateProvider;
    private GameEngine gameEngine;

    public GameEngineService(LocationService locationService, GameStateProvider gameStateProvider, GameEngine gameEngine) {
        this.locationService = locationService;
        this.gameStateProvider = gameStateProvider;
        this.gameEngine = gameEngine;
    }

    @Override
    public void onCreate() {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Game engine service created");
        }

        locationService = new LocationServiceImpl(this);

        CheckpointAreaEnteredListener checkpointAreaEnteredListener
                = new CheckpointAreaEnteredListener(locationService, gameEngine, gameStateProvider);

        registerReceiver(checkpointAreaEnteredListener,
                new IntentFilter("Entered checkpoint area"));

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Game engine service started");
        }

        locationService.registerCheckpointAreas(
                gameStateProvider.getCurrentQuestState().getNextCheckpoints());

        locationService.start();

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Game engine service destroyed");
        }

        locationService.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {

        if (Log.isLoggable("QUESTER", Log.DEBUG)) {
            Log.d("QUESTER", "Game engine service binded");
        }
        return null;
    }

}
