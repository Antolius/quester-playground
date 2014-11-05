package com.example.josip.engine.multiplayer;

import com.example.josip.engine.Processor;
import com.example.josip.engine.state.GameStateProvider;
import com.example.josip.model.Checkpoint;

import retrofit.RestAdapter;

public class MultiplayerProcessor implements Processor {

    private GameStateProvider gameStateProvider;
    private QuestSynchronizedCallback questSynchronizedCallback;

    private MultiplayerNetworkAPI multiplayerNetworkAPI;

    public MultiplayerProcessor(GameStateProvider gameStateProvider,
                                QuestSynchronizedCallback questSynchronizedCallback,
                                MultiplayerNetworkAPI multiplayerNetworkAPI) {
        this.gameStateProvider = gameStateProvider;
        this.questSynchronizedCallback = questSynchronizedCallback;
        this.multiplayerNetworkAPI = multiplayerNetworkAPI;
    }

    public MultiplayerProcessor(String baseUrl,
                                GameStateProvider gameStateProvider,
                                QuestSynchronizedCallback questSynchronizedCallback) {
        this.gameStateProvider = gameStateProvider;
        this.questSynchronizedCallback = questSynchronizedCallback;

        this.multiplayerNetworkAPI = new RestAdapter.Builder()
                .setServer(baseUrl).build().create(MultiplayerNetworkAPI.class);
    }

    @Override
    public void process(Checkpoint currentCheckpoint) {

        if (multiplayerNetworkAPI.processCheckpoint(currentCheckpoint)) {
            questSynchronizedCallback.questSynchronized(currentCheckpoint);
        }
    }
}
