package com.example.josip.engine.state;

import com.example.josip.model.QuestState;

import org.mozilla.javascript.NativeObject;

public interface GameStateProvider {

    public QuestState getCurrentQuestState();

    public void setCurrentQuestState(QuestState state);

    public NativeObject getPersistentGameObject();

    public void saveState();

}
