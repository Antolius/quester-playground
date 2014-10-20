package com.example.josip.jstest;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.PersistentGameObject;
import com.example.josip.model.QuestState;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSConstructor;
import org.mozilla.javascript.annotations.JSFunction;

import java.util.Collection;

/**
 * Created by Josip on 12/08/2014!
 */
public class JavaScriptApiImpl extends ScriptableObject{

    private QuestState questState;
    private PersistentGameObject persistentGameObject;
    private Checkpoint eventCheckpointContext;

    public JavaScriptApiImpl() {

    }

    @JSConstructor
    public JavaScriptApiImpl(QuestState questState, PersistentGameObject persistentGameObject, Checkpoint checkpoint) {
        this.questState = questState;
        this.persistentGameObject = persistentGameObject;
        this.eventCheckpointContext = checkpoint;
    }

    @JSFunction
    public long[] getVisibleCheckpoints() {
        Collection<Checkpoint> children = questState.getQuestGraph().getChildren(eventCheckpointContext);

        long[] visibleCheckpointIds = new long[children.size()];

        int i = 0;
        for(Checkpoint checkpoint : children) {
            visibleCheckpointIds[i++] = checkpoint.getId();
        }

        return visibleCheckpointIds;
    }

    @JSFunction
    public long getEventContextCheckpoint() {
        return eventCheckpointContext.getId();
    }

    @JSFunction
    public long[] getVisitedCheckpoints() {
        long[] visitedCheckpointIds = new long[questState.getVisitedCheckpoints().size()];

        int i = 0;
        for (Checkpoint checkpoint : questState.getVisitedCheckpoints()) {
            visitedCheckpointIds[i++] = checkpoint.getId();
        }

        return visitedCheckpointIds;
    }

    @JSFunction
    public Object getPersistantProperty(String key) {
        return persistentGameObject.getProperty(key);
    }

    @JSFunction
    public void setPersistantProperty(String key, Object value) {
        persistentGameObject.putProperty(key, value);
    }

    @Override
    public String getClassName() {
        return "GameState";
    }
}
