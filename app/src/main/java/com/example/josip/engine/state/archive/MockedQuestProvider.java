package com.example.josip.engine.state.archive;

import android.content.Context;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.CheckpointBuilder;
import com.example.josip.model.Quest;
import com.example.josip.model.QuestBuilder;
import com.example.josip.model.area.CircleArea;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Josip on 18/10/2014!
 */
public class MockedQuestProvider {

    public static Quest getMockedQuest(Context context) {

        QuestBuilder questBuilder = new QuestBuilder("mocked quest");

        Checkpoint a = new CheckpointBuilder(1L)
                .name("checkpoint 1")
                .isRoot(true)
                .eventScript(writeFile(context, "eventScript1.js",
                        "var onEnter = function(arg){return true;}"))
                .viewHtml(writeFile(context, "view1.html", ""))
                .area(new CircleArea.CircleAreaBuilder()
                                .centerLatitude(45.0)
                                .centerLongitude(15.0)
                                .radius(10000000.0)
                                .build()
                ).build();

        Checkpoint b = new CheckpointBuilder(2L)
                .name("checkpoint 2")
                .isRoot(false)
                .eventScript(writeFile(context, "eventScript2.js",
                        "var onEnter = function(arg){return true;}"))
                .viewHtml(writeFile(context, "view2.html", ""))
                .area(new CircleArea.CircleAreaBuilder()
                                .centerLatitude(45.0)
                                .centerLongitude(15.0)
                                .radius(10000000000000000.0)
                                .build()
                ).build();

        Checkpoint c = new CheckpointBuilder(3L)
                .name("checkpoint 3")
                .isRoot(false)
                .eventScript(writeFile(context, "eventScript3.js",
                        "var onEnter = function(arg){return true;}"))
                .viewHtml(writeFile(context, "view3.html", ""))
                .area(new CircleArea.CircleAreaBuilder()
                                .centerLatitude(45.0)
                                .centerLongitude(15.0)
                                .radius(1000000000.0)
                                .build()
                ).build();

        Checkpoint d = new CheckpointBuilder(4L)
                .name("checkpoint 4")
                .isRoot(false)
                .eventScript(writeFile(context, "eventScript4.js",
                        "var onEnter = function(arg){return true;}"))
                .viewHtml(writeFile(context, "view4.html", ""))
                .area(new CircleArea.CircleAreaBuilder()
                                .centerLatitude(45.0)
                                .centerLongitude(15.0)
                                .radius(10000000000.0)
                                .build()
                ).build();

        return questBuilder
                .addCheckpoint(a)
                .addCheckpoint(b)
                .addCheckpoint(c)
                .addCheckpoint(d)
                .addConnection(a, b)
                .addConnection(b, c)
                .addConnection(c, d)
                .build();

    }

    private static File writeFile(Context context, String fileName, String fileContent) {
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContent.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new File(context.getFilesDir(), fileName);
    }
}
