package com.example.josip.engine.multiplayer;

import com.example.josip.model.Checkpoint;

import retrofit.http.Body;
import retrofit.http.POST;

public interface MultiplayerNetworkAPI {

    @POST("/api/process/checkpoint")
    Boolean processCheckpoint(@Body Checkpoint currentCheckpoint);
}

