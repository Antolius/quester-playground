package com.example.josip.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.josip.gameService.engine.GameEngine;
import com.example.josip.gameService.locationService.LocationService;
import com.example.josip.gameService.stateProvider.GameStateProvider;
import com.example.josip.model.Checkpoint;

public class CheckpointAreaEnteredListener extends BroadcastReceiver {

    private final LocationService locationService;
    private final GameEngine gameEngine;
    private final GameStateProvider gameStateProvider;

    public CheckpointAreaEnteredListener(LocationService locationService,
                                         GameEngine gameEngine,
                                         GameStateProvider gameStateProvider) {
        this.locationService = locationService;
        this.gameEngine = gameEngine;
        this.gameStateProvider = gameStateProvider;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Checkpoint checkpoint = (Checkpoint) intent.getSerializableExtra("checkpoint");

        try {
            if (!gameEngine.onCheckpointAreaEnter(checkpoint)) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        gameStateProvider.getCurrentQuestState().getVisitedCheckpoints().add(checkpoint);
        //post notification
        locationService.unregisterCheckpointArea(checkpoint);

        locationService.registerCheckpointAreas(
                gameStateProvider.getCurrentQuestState().getNextCheckpoints());
    }

}
