package com.example.josip.engine.state;

import com.example.josip.model.QuestState;

import org.mozilla.javascript.NativeObject;

public class GameStateProviderImpl implements GameStateProvider {

    public QuestState questState;
    public NativeObject persistentGameObject;

    @Override
    public QuestState getCurrentQuestState() {
        return questState;
    }

    @Override
    public void setCurrentQuestState(QuestState questState) {
        this.questState = questState;
    }

    @Override
    public NativeObject getPersistentGameObject() {
        return persistentGameObject;
    }

    @Override
    public void saveState() {
        //TODO fancy shit
    }

}
