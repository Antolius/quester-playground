package com.example.josip.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Quest {

    @Attribute
    private long id;

    @Element
    private String name;
    private QuestMetaData metaData;

    @Element
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
