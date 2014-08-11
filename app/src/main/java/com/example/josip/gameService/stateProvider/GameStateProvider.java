package com.example.josip.gameService.stateProvider;

import com.example.josip.model.PersistentGameObject;
import com.example.josip.model.QuestState;

/**
 * Created by Josip on 11/08/2014!
 */
public interface GameStateProvider {

    public QuestState getCurrentQuestState();

    public PersistentGameObject getPersistantGameObject();

}
