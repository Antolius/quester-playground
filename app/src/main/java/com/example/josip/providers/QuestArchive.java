package com.example.josip.providers;

import android.content.Context;

import com.example.josip.model.Quest;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class QuestArchive {

    private Context context;
    private QuestSerializer serializer;

    public QuestArchive(Context context) {
        this.context = context;
        this.serializer = new QuestSerializer();
    }

    public void persistQuest(Quest quest) {

        FileOutputStream outputStream;

        try {
            outputStream =
                    context.openFileOutput(
                            quest.getId() + "_" + quest.getName() + ".json", Context.MODE_PRIVATE);
            outputStream.write(serializer.serialize(quest).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Quest getQuestFromArchive(long id, String name) throws IOException, JSONException {

        BufferedReader reader =
                new BufferedReader(
                        new FileReader(
                                new File(context.getFilesDir(), id + "_" + name + ".json"))
                );

        String json = "";
        String line;
        while ((line = reader.readLine()) != null) {
            json += line;
        }

        return serializer.deserialize(json);
    }
}
