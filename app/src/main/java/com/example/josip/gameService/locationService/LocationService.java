package com.example.josip.gameService.locationService;

import com.example.josip.model.Checkpoint;

import java.util.Set;

/**
 * Created by Josip on 11/08/2014!
 */
public interface LocationService {

    public void start();

    public void stop();

    public void registerCheckpointAreas(Set<Checkpoint> checkpoints);

    void unregisterCheckpointArea(Checkpoint checkpoint);
}
