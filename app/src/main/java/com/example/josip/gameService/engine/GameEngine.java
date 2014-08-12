package com.example.josip.gameService.engine;

import com.example.josip.model.Checkpoint;

/**
 * Created by Josip on 11/08/2014!
 */
public interface GameEngine {

    public void onCheckpointAreaEnter(final Checkpoint checkpoint);

    public void onCheckpointAreaExit(final Checkpoint checkpoint);

}
