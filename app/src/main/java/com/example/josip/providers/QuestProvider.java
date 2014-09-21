package com.example.josip.providers;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.CheckpointArea;
import com.example.josip.model.Circle;
import com.example.josip.model.Point;
import com.example.josip.model.Quest;
import com.example.josip.model.QuestGraph;
import com.example.josip.model.QuestMetaData;
import com.example.josip.model.enums.MessurmentUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Josip on 21/09/2014!
 */
public class QuestProvider {

    final static Random random = new Random();
    private static long questCount = 0;

    public static ArrayList<Quest> getMockedQuests(int count) {
        ArrayList<Quest> quests = new ArrayList<Quest>(count);

        for (int i = 0; i < count; ++i) {
            quests.add(getMockedQuest());
        }

        return quests;
    }

    private static Quest getMockedQuest() {
        Quest quest = new Quest();

        quest.setId(questCount++);
        quest.setName("Mocked Quest #-" + quest.getId());
        quest.setQuestGraph(getMockedQuestGraph());
        quest.setMetaData(new QuestMetaData());

        return quest;
    }

    private static QuestGraph getMockedQuestGraph() {
        Checkpoint checkpoint1 = getMockedCheckpoint(1), checkpoint2 = getMockedCheckpoint(2);

        QuestGraph questGraph = new QuestGraph(Arrays.asList(checkpoint1, checkpoint2));
        questGraph.addEdge(checkpoint1, checkpoint2);

        return questGraph;
    }

    private static Checkpoint getMockedCheckpoint(long id) {
        Checkpoint checkpoint = new Checkpoint();

        checkpoint.setId(id);
        checkpoint.setName("Mocked checkpoint #-" + checkpoint.getId());

        checkpoint.setArea(new CheckpointArea() {
            @Override
            public boolean isInside(Point point) {
                return false;
            }

            @Override
            public double distanceFrom(Point point, MessurmentUnit messurmentUnit) {
                return 100;
            }

            @Override
            public Circle aproximatingCircle() {
                Circle circle = new Circle();

                circle.setCenter(new Point(45.8 + random.nextDouble() / 10, 15.9 + random.nextDouble() / 10));
                circle.setRadius(random.nextDouble());

                return circle;
            }
        });

        return checkpoint;
    }
}
