package com.example.josip.engine.state;

import android.content.Context;

import com.example.josip.engine.script.FileManager;
import com.example.josip.engine.script.api.JsonUtils;
import com.example.josip.model.QuestState;

import org.mozilla.javascript.NativeObject;

public class GameStateProvider {

    public QuestState questState;
    public NativeObject persistentGameObject;

    public QuestState getCurrentQuestState() {
        return questState;
    }

    public void setCurrentQuestState(QuestState questState) {
        this.questState = questState;
    }

    public NativeObject getPersistentGameObject() {

        return persistentGameObject;
    }

    public void saveState(Context context) {
        //TODO fancy shit

        String json = JsonUtils.fromNativeObject(persistentGameObject);
        new FileManager().write(context, "PGO.json", json);
    }

}
