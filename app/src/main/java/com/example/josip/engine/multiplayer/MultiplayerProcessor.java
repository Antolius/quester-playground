package com.example.josip.engine.multiplayer;

import com.example.josip.engine.state.GameStateProvider;
import com.example.josip.model.Checkpoint;

import retrofit.RestAdapter;

public class MultiplayerProcessor {

    private GameStateProvider gameStateProvider;
    private QuestSynchronizedCallback questSynchronizedCallback;

    private MultiplayerNetworkAPI multiplayerNetworkAPI;

    public MultiplayerProcessor(String baseUrl,
                                GameStateProvider gameStateProvider,
                                QuestSynchronizedCallback questSynchronizedCallback) {
        this.gameStateProvider = gameStateProvider;
        this.questSynchronizedCallback = questSynchronizedCallback;

        this.multiplayerNetworkAPI = new RestAdapter.Builder()
                .setServer(baseUrl).build().create(MultiplayerNetworkAPI.class);
    }

    public void synchronize(Checkpoint currentCheckpoint) {

        if (multiplayerNetworkAPI.processCheckpoint(currentCheckpoint)) {
            questSynchronizedCallback.questSynchronized(currentCheckpoint);
        }
    }
}
