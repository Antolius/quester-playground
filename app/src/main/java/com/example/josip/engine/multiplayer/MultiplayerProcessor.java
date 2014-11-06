package com.example.josip.engine.multiplayer;

import com.example.josip.engine.Processor;
import com.example.josip.model.Checkpoint;

import retrofit.RestAdapter;

public class MultiplayerProcessor implements Processor {

    private QuestSynchronizedCallback questSynchronizedCallback;

    private MultiplayerNetworkAPI multiplayerNetworkAPI;

    public MultiplayerProcessor(MultiplayerNetworkAPI multiplayerNetworkAPI,
                                QuestSynchronizedCallback questSynchronizedCallback) {
        this.questSynchronizedCallback = questSynchronizedCallback;
        this.multiplayerNetworkAPI = multiplayerNetworkAPI;
    }

    public MultiplayerProcessor(String baseUrl,
                                QuestSynchronizedCallback questSynchronizedCallback) {

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
