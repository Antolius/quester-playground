package com.example.josip.engine.state;

import com.example.josip.model.PersistentGameObject;
import com.example.josip.model.QuestState;

/**
 * Created by tdubravcevic on 10.8.2014!
 */
public class GameStateProviderImpl implements GameStateProvider {

    public QuestState questState;

    public PersistentGameObject persistentGameObject;

    @Override
    public QuestState getCurrentQuestState() {
        return questState;
    }

    @Override
    public PersistentGameObject getPersistantGameObject() {
        return persistentGameObject;
    }

    @Override
    public void saveState() {
        //TODO fancy shit
    }

}
