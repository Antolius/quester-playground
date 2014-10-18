package com.example.josip.providers;

import android.content.Context;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.CheckpointBuilder;
import com.example.josip.model.Quest;
import com.example.josip.model.QuestBuilder;
import com.example.josip.model.enums.CircleArea;

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
                .eventScript(writeFile(context, "eventScript1.js", ""))
                .viewHtml(writeFile(context, "view1.html", ""))
                .area(new CircleArea.CircleAreaBuilder()
                                .centerLatitude(1.0)
                                .centerLongitude(1.0)
                                .radius(1.0)
                                .build()
                ).build();

        Checkpoint b = new CheckpointBuilder(2L)
                .name("checkpoint 2")
                .isRoot(false)
                .eventScript(writeFile(context, "eventScript2.js", ""))
                .viewHtml(writeFile(context, "view2.html", ""))
                .area(new CircleArea.CircleAreaBuilder()
                                .centerLatitude(1.0)
                                .centerLongitude(1.0)
                                .radius(1.0)
                                .build()
                ).build();

        return questBuilder.addCheckpoint(a).addCheckpoint(b).addConnection(a, b).build();

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
