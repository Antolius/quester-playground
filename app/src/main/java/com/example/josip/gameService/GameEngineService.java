package com.example.josip.gameService;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.example.josip.gameService.engine.GameEngine;
import com.example.josip.gameService.engine.impl.GameEngineImpl;
import com.example.josip.gameService.locationService.LocationService;
import com.example.josip.gameService.locationService.impl.LocationServiceImpl;
import com.example.josip.gameService.stateProvider.GameStateProvider;
import com.example.josip.gameService.stateProvider.impl.GameStateProviderImpl;
import com.example.josip.location.CheckpointAreaEnteredListener;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.Quest;

import java.util.ArrayList;

/**
 * Created by tdubravcevic on 10.8.2014!
 */
public class GameEngineService extends Service {

    private LocationService locationService;
    private GameStateProvider gameStateProvider;
    private GameEngine gameEngine;

    public GameEngineService() {
        this.locationService = new LocationServiceImpl(this);
        this.gameStateProvider = new GameStateProviderImpl();
        this.gameEngine = new GameEngineImpl(gameStateProvider);
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

        Quest quest = (Quest) intent.getSerializableExtra("quest");

        gameStateProvider.getCurrentQuestState().setQuestGraph(quest.getQuestGraph());
        gameStateProvider.getCurrentQuestState().setVisitedCheckpoints(new ArrayList<Checkpoint>());

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
