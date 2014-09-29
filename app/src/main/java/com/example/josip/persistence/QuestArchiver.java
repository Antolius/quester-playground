package com.example.josip.persistence;

import com.example.josip.model.Quest;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

/**
 * Created by tdubravcevic on 29.9.2014!
 */
public class QuestArchiver {

    public void archive(Quest quest) throws Exception {

        Serializer serializer = new Persister();
        File file = new File(quest.getName()+".xml");
        serializer.write(quest, file);
    }
}
