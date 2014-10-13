package com.example.josip.model;

import java.util.ArrayList;

/**
 * Created by tdubravcevic on 13.10.2014!
 */
public class QuestBuilder {

    private Quest quest;

    public QuestBuilder(String name){

        quest = new Quest();
        quest.setName(name);
        quest.setQuestGraph(new QuestGraph(new ArrayList<Checkpoint>()));
    }

    public QuestBuilder addCheckpoint(Checkpoint checkpoint){
        quest.getQuestGraph().getAllCheckpoints().add(checkpoint);
        return this;
    }

    public QuestBuilder addConnection(Checkpoint start, Checkpoint end){
        quest.getQuestGraph().addEdge(start, end);
        return this;
    }

    public Quest build(){
        return quest;
    }
}
