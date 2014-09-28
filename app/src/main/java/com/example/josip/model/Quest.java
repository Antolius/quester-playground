package com.example.josip.model;

import java.io.Serializable;

/**
 * Created by Josip on 10/08/2014.
 */
public class Quest implements Serializable {

    private long id;
    private String name;
    private QuestMetaData metaData;
    private QuestGraph questGraph;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuestMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(QuestMetaData metaData) {
        this.metaData = metaData;
    }

    public QuestGraph getQuestGraph() {
        return questGraph;
    }

    public void setQuestGraph(QuestGraph questGraph) {
        this.questGraph = questGraph;
    }
}
