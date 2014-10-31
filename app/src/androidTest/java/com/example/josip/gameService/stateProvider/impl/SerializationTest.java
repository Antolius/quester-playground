package com.example.josip.gameService.stateProvider.impl;


import android.test.AndroidTestCase;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.CheckpointBuilder;
import com.example.josip.model.Quest;
import com.example.josip.model.QuestBuilder;
import com.example.josip.engine.state.archive.QuestSerializer;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.example.josip.model.area.CircleArea.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class SerializationTest{

    @Test
    public void serializeTest() throws JSONException {

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

        assertEquals("testQuest", deserializedQuest.getName());

    }
}