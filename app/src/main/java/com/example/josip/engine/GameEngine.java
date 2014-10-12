package com.example.josip.engine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.josip.engine.location.LocationProcessor;
import com.example.josip.engine.location.LocationReachedCallback;
import com.example.josip.engine.script.CheckpointVisitedCallback;
import com.example.josip.engine.script.ScriptProcessor;
import com.example.josip.gameService.stateProvider.GameStateProvider;
import com.example.josip.gameService.stateProvider.impl.GameStateProviderImpl;
import com.example.josip.model.Checkpoint;

/**
 * Created by Josip on 12/10/2014!
 */
public class GameEngine extends Service {

    private LocationProcessor locationProcessor;
    private ScriptProcessor scriptProcessor;

    private GameStateProvider gameStateProvider;

    @Override
    public void onCreate() {

        gameStateProvider = new GameStateProviderImpl();

        locationProcessor = new LocationProcessor(new LocationReachedCallback() {
            @Override
            public void locationReached(Checkpoint reachedCheckpoint) {
                scriptProcessor.processCheckpoint(reachedCheckpoint);
            }
        });

        scriptProcessor = new ScriptProcessor(gameStateProvider.getPersistantGameObject(), new CheckpointVisitedCallback() {
            @Override
            public void checkpointVisited(Checkpoint visitedCheckpoint) {
                gameStateProvider.getCurrentQuestState().getVisitedCheckpoints().add(visitedCheckpoint);
                locationProcessor.trackLocation(gameStateProvider.getCurrentQuestState().getQuestGraph().getChildren(visitedCheckpoint));

                gameStateProvider.saveState();

                //TODO: okinuti notifikaciju ili tako nesto :)
            }
        });

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
