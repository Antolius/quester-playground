package com.example.josip.gameService.stateProvider.impl;

import android.test.AndroidTestCase;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.Circle;
import com.example.josip.model.Point;
import com.example.josip.model.Quest;
import com.example.josip.model.QuestGraph;
import com.example.josip.model.enums.CircleArea;

import org.json.JSONException;

import java.util.Arrays;

/**
 * Created by tdubravcevic on 13.10.2014!
 */
public class QuestSerializerTest extends AndroidTestCase {

    public void test1() throws JSONException {

        Quest quest = new Quest();

        Checkpoint checkpointA = new Checkpoint();
        checkpointA.setId(1);
        checkpointA.setName("A");
        Circle circle = new Circle();
        circle.setCenter(new Point(0.0,0.0));
        circle.setRadius(1.0);
        checkpointA.setArea(new CircleArea(circle));

        Checkpoint checkpointB = new Checkpoint();
        checkpointB.setId(2);
        checkpointB.setName("B");
        circle = new Circle();
        circle.setCenter(new Point(3.0,3.0));
        circle.setRadius(1.0);
        checkpointB.setArea(new CircleArea(circle));

        QuestGraph graph = new QuestGraph(Arrays.asList(checkpointA, checkpointB));
        graph.addEdge(checkpointA, checkpointB);

        quest.setQuestGraph(graph);

        QuestSerializer serializer = new QuestSerializer();

        String json = serializer.serialize(quest);

        Quest quest1 = serializer.deserialize(json);

        quest1.getQuestGraph();
    }
}
