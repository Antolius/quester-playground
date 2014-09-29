package com.example.josip.persistence;

import android.test.AndroidTestCase;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.Quest;
import com.example.josip.model.QuestGraph;

import java.util.Arrays;

/**
 * Created by tdubravcevic on 29.9.2014!
 */
public class QuestArchiverTest extends AndroidTestCase {

    public void testTest() throws Exception {

        QuestArchiver archiver = new QuestArchiver();

        Quest quest = new Quest();
        quest.setName("testQuest");

        Checkpoint checkpoint1= new Checkpoint();
        checkpoint1.setId(0);
        checkpoint1.setName("prvi");

        Checkpoint checkpoint2= new Checkpoint();
        checkpoint2.setId(1);
        checkpoint2.setName("drugi");

        QuestGraph graph= new QuestGraph(Arrays.asList(checkpoint1, checkpoint2));
        graph.addEdge(checkpoint1, checkpoint2);

        quest.setQuestGraph(graph);

        archiver.archive(quest);
    }

}
