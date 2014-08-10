package com.example.josip.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Josip on 10/08/2014.
 */
public class Quest {

    private long id;
    private String name;
    private MetaData metaData;
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

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public QuestGraph getQuestGraph() {
        return questGraph;
    }

    public void setQuestGraph(QuestGraph questGraph) {
        this.questGraph = questGraph;
    }
}
