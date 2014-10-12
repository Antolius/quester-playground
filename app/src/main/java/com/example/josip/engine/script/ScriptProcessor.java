package com.example.josip.engine.script;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.PersistentGameObject;

/**
 * Created by Josip on 12/10/2014!
 */
public class ScriptProcessor {

    private PersistentGameObject persistentGameObject;
    private CheckpointVisitedCallback checkpointVisitedCallback;

    public ScriptProcessor(PersistentGameObject persistentGameObject, CheckpointVisitedCallback checkpointVisitedCallback) {
        this.persistentGameObject = persistentGameObject;
        this.checkpointVisitedCallback = checkpointVisitedCallback;
    }

    public void processCheckpoint(Checkpoint reachedCheckpoint) {
        //TODO do your yob!
    }
}
