package com.example.josip.gameService.engine.impl;

import android.test.AndroidTestCase;

import com.example.josip.gameService.GameContext;
import com.example.josip.gameService.stateProvider.GameStateProvider;
import com.example.josip.model.Checkpoint;
import com.example.josip.model.PersistentGameObject;
import com.example.josip.model.Quest;
import com.example.josip.model.QuestGraph;
import com.example.josip.model.QuestState;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Josip on 12/08/2014!
 */
public class JavaScriptEngineTest extends AndroidTestCase{

    QuestState questState;
    PersistentGameObject prePersistentGameObject;
    Checkpoint checkpoint1, checkpoint2;

    @Mock
    GameContext gameContext;

    @InjectMocks
    JavaScriptEngineImpl javaScriptEngine;

    public void setUp() {
        System.setProperty("dexmaker.dexcache", getContext().getCacheDir().getPath());

        MockitoAnnotations.initMocks(this);

        prePersistentGameObject = new PersistentGameObject();
        prePersistentGameObject.putProperty("testProperty", 3.14159);

        questState = new QuestState();
        questState.setQuestGraph(getSetUpQuestGraph());

        checkpoint1 = new Checkpoint();
        checkpoint2 = new Checkpoint();

        checkpoint1.setId(1L);
        checkpoint2.setId(2L);

        QuestState questState = mock(QuestState.class);
        when(questState.getQuestGraph()).thenReturn(getSetUpQuestGraph());
        when(questState.getVisitedCheckpoints()).thenReturn(Collections.<Checkpoint> emptyList());
        when(questState.getActiveCheckpoint()).thenReturn(null);

        GameStateProvider gameStateProvider = mock(GameStateProvider.class);
        when(gameStateProvider.getCurrentQuestState()).thenReturn(questState);
        when(gameStateProvider.getPersistantGameObject()).thenReturn(prePersistentGameObject);

        when(gameContext.getGameStateProvider()).thenReturn(gameStateProvider);
    }

    private QuestGraph getSetUpQuestGraph() {
        QuestGraph graph = new QuestGraph(Arrays.asList(checkpoint1, checkpoint2));
        graph.addEdge(checkpoint1, checkpoint2);
        graph.addEdge(checkpoint1, checkpoint1);

        return graph;
    }


    public void testDimpleRunScript() {

    }

}
