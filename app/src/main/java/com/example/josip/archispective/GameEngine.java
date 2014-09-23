package com.example.josip.archispective;

import android.location.LocationListener;

import com.example.josip.model.Checkpoint;

/**
 * Created by tdubravcevic on 30.8.2014!
 */
public class GameEngine {

    private GameStateProvider gameStateProvider;

    public GameEngine(final GameStateProvider gameStateProvider, LocationService locationService) {
        this.gameStateProvider = gameStateProvider;

        locationService.addEnterCheckpointListener(new EnterCheckpointListener() {
            @Override
            public void onEnterCheckpoint(Checkpoint checkpoint) {
                   gameStateProvider.setCurrentCheckpoint();
            }
        });
    }

    public void work(){}
}
