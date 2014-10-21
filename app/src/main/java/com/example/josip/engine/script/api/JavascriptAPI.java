package com.example.josip.engine.script.api;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.PersistentGameObject;
import com.example.josip.model.QuestState;

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

    public JavascriptAPI(PersistentGameObject persistentGameObject){
        this.persistentGameObject = persistentGameObject;
        this.questState = new QuestState();
        this.questState.setVisitedCheckpoints(new ArrayList<Checkpoint>());
        this.currentCheckpoint = new Checkpoint();
    }

    @Override
    public String getClassName() {
        return "API";
    }

    @JSFunction
    public List<Checkpoint> getVisitedCheckpoints() {
        return questState.getVisitedCheckpoints();
    }
    /*
    @JSFunction
    @Override
    public Set<Checkpoint> getNextCheckpoints() {
       // return questState.getQuestGraph().getChildren(currentCheckpoint);
        return null;
    }
*/
    @JSFunction
    public Checkpoint getCurrentCheckpoint() {
        return currentCheckpoint;
    }
        /*
    @JSFunction
    @Override
    public void addConnection(Checkpoint from, Checkpoint to) {
       // questState.getQuestGraph().addEdge(from, to);
    }
    */

    @JSFunction
    @Override
    public PersistentGameObject getPersistenceObject() {
        return persistentGameObject;
    }
}
