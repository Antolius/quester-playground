package com.example.josip.engine.script.api;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.PersistentGameObject;
import com.example.josip.model.QuestState;

import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JavascriptAPI extends ScriptableObject implements API {

    public static final String ENTRY_POINT_NAME = "API";

    private NativeObject persistentGameObject;
    private QuestState questState;
    private Checkpoint currentCheckpoint;

    public JavascriptAPI() {}

    public void setPersistentGameObject(NativeObject persistentGameObject) {
        this.persistentGameObject = persistentGameObject;
    }

    public void setQuestState(QuestState questState) {
        this.questState = questState;
    }

    public void setCurrentCheckpoint(Checkpoint currentCheckpoint) {
        this.currentCheckpoint = currentCheckpoint;
    }

    public JavascriptAPI(JavascriptAPI api){
        this.persistentGameObject = api.persistentGameObject;
        this.questState = api.questState;
        this.currentCheckpoint = api.currentCheckpoint;
    }

    @Override
    public String getClassName() {
        return ENTRY_POINT_NAME;
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
    public NativeObject getPersistenceObject() {
        return persistentGameObject;
    }

    @JSFunction
    public void setPersistenceObject(NativeObject nativeObject) {
        this.persistentGameObject = nativeObject;
    }
}
