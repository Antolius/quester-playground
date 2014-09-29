package com.example.josip.persistence;

import android.test.AndroidTestCase;

import com.example.josip.model.Quest;

/**
 * Created by tdubravcevic on 29.9.2014!
 */
public class QuestArchiverTest extends AndroidTestCase {

    public void testTest() throws Exception {

        QuestArchiver archiver = new QuestArchiver();

        Quest quest = new Quest();
        quest.setName("testQuest");

        archiver.archive(quest);
    }

}
