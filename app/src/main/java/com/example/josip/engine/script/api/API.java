package com.example.josip.engine.script.api;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.PersistentGameObject;

import java.util.List;
import java.util.Set;

public interface API {

    List<Checkpoint> getVisitedCheckpoints();

    public Set<Checkpoint> getNextCheckpoints();

    public Checkpoint getCurrentCheckpoint();

    public PersistentGameObject getPersistenceObject();
}
