package com.example.josip.gameService.stateProvider.impl;

import com.example.josip.gameService.stateProvider.GameStateProvider;
import com.example.josip.model.PersistentGameObject;
import com.example.josip.model.QuestState;

/**
 * Created by tdubravcevic on 10.8.2014!
 */
public class GameStateProviderImpl implements GameStateProvider {

    private QuestState questState;

    private PersistentGameObject persistentGameObject;

    @Override
    public QuestState getCurrentQuestState() {
        return questState;
    }

    @Override
    public PersistentGameObject getPersistantGameObject() {
        return persistentGameObject;
    }


}
