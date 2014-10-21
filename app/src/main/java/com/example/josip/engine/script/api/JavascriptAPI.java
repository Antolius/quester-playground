package com.example.josip.engine.script.api;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.PersistentGameObject;
import com.example.josip.model.QuestState;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JavascriptAPI extends ScriptableObject implements API {

    private PersistentGameObject persistentGameObject;
    private QuestState questState;
    private Checkpoint currentCheckpoint;

    public JavascriptAPI() {
        this.persistentGameObject = new PersistentGameObject();
        this.questState = new QuestState();
        this.questState.setVisitedCheckpoints(new ArrayList<Checkpoint>());
        this.currentCheckpoint = new Checkpoint();
    }

    public JavascriptAPI(JavascriptAPI api){
        this.persistentGameObject = api.persistentGameObject;
        this.questState = api.questState;
        this.currentCheckpoint = api.currentCheckpoint;
    }

    @Override
    public String getClassName() {
        return "API";
    }

    @JSFunction
    @Override
    public List<Checkpoint> getVisitedCheckpoints() {
        return questState.getVisitedCheckpoints();
    }

    @JSFunction
    @Override
    public Set<Checkpoint> getNextCheckpoints() {
        return questState.getQuestGraph().getChildren(currentCheckpoint);
    }

    @JSFunction
    @Override
    public Checkpoint getCurrentCheckpoint() {
        return currentCheckpoint;
    }


    @JSFunction
    @Override
    public PersistentGameObject getPersistenceObject() {
        return persistentGameObject;
    }
}
