package com.example.josip.engine.script.api;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.PersistentGameObject;

import org.mozilla.javascript.annotations.JSFunction;

import java.util.List;
import java.util.Set;

public interface API {

    /*
    List<Checkpoint> getVisitedCheckpints();

    public Set<Checkpoint> getNextCheckpoints();

    public Checkpoint getCurrentCheckpoint();

    public void addConnection(Checkpoint from, Checkpoint to);
    */

    public PersistentGameObject getPersistenceObject();
}
