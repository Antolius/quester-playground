package com.example.josip.gameService.stateProvider.impl;

import android.test.AndroidTestCase;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.CheckpointBuilder;
import com.example.josip.model.Quest;
import com.example.josip.model.QuestBuilder;
import com.example.josip.engine.state.archive.QuestSerializer;

import org.json.JSONException;

import static com.example.josip.model.area.CircleArea.*;


public class QuestSerializerTest extends AndroidTestCase {

    public void testSerializeQuestAndDeserializeIt() throws JSONException {

        Checkpoint checkpointA =
                new CheckpointBuilder(1L)
                        .name("A")
                        .area(new CircleAreaBuilder()
                                .centerLatitude(0.1)
                                .centerLongitude(0.1)
                                .radius(1.0)
                                .build())
                        .build();

        Checkpoint checkpointB =
                new CheckpointBuilder(2L)
                        .name("B")
                        .area(new CircleAreaBuilder()
                                .centerLatitude(2.0)
                                .centerLongitude(2.0)
                                .radius(1.0)
                                .build())
                        .build();

        Quest quest = new QuestBuilder("testQuest")
                .addCheckpoint(checkpointA)
                .addCheckpoint(checkpointB)
                .addConnection(checkpointA, checkpointB)
                .build();

        QuestSerializer serializer = new QuestSerializer();

        String json = serializer.serialize(quest);

        Quest deserializedQuest = serializer.deserialize(json);

        deserializedQuest.getQuestGraph();
    }
}
