package com.example.josip.engine.state.archive;

import android.database.sqlite.SQLiteDatabase;

import com.example.josip.model.Quest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class QuestRepositoryTest {

    @Test
    public void questIsPersistedCorrectly() throws URISyntaxException {

        SQLiteDatabase db = Robolectric.application.openOrCreateDatabase("test2", 0, null);

        db.execSQL("CREATE TABLE quests(" +
                "id           INTEGER PRIMARY KEY     NOT NULL," +
                "name          TEXT     NOT NULL" +
                ");");
        db.execSQL("CREATE TABLE checkpoints(" +
                "id            INTEGER PRIMARY KEY   NOT NULL," +
                "quest_id      INTEGER   NOT NULL," +
                "name          TEXT     NOT NULL" +
                ");");
        db.execSQL("CREATE TABLE connections(" +
                "parent_id     INTEGER   NOT NULL," +
                "child_id      INTEGER   NOT NULL," +
                "FOREIGN KEY (parent_id) REFERENCES checkpoints(id)," +
                "FOREIGN KEY (child_id) REFERENCES checkpoints(id)," +
                "PRIMARY KEY (parent_id, child_id)" +
                ");");

        QuestRepository repository = new QuestRepository(db);

        Quest quest = MockedQuestProvider.getMockedQuest(Robolectric.application);

        Quest persistedQuest = repository.persistQuest(quest);

        Quest persisted = repository.queryQuest(persistedQuest.getId());

        assertEquals(quest.getName(), persisted.getName());
    }

}
