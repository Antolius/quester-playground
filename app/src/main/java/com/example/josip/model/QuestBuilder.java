package com.example.josip.model;

import com.example.josip.model.graph.QuestGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tdubravcevic on 13.10.2014!
 */
public class QuestBuilder {

    private Quest quest;

    private ArrayList<Checkpoint> checkpoints;
    private Map<Checkpoint, List<Checkpoint>> checkpointMap;

    public QuestBuilder(String name){

        quest = new Quest();
        quest.setName(name);
        checkpoints = new ArrayList<Checkpoint>();
        checkpointMap = new HashMap<Checkpoint, List<Checkpoint>>();
    }

    public QuestBuilder id(long id){
        quest.setId(id);
        return this;
    }

    public QuestBuilder addCheckpoint(Checkpoint checkpoint){
        checkpointMap.put(checkpoint, new ArrayList<Checkpoint>());
        checkpoints.add(checkpoint);
        //Set<Checkpoint> checkpointSet = quest.getQuestGraph().getAllCheckpoints();
        //checkpointSet.add(checkpoint);
        return this;
    }

    public QuestBuilder addConnection(Checkpoint start, Checkpoint end){
        checkpointMap.get(start).add(end);
        //quest.getQuestGraph().addEdge(start, end);
        return this;
    }

    public Quest build(){
        quest.setQuestGraph(new QuestGraph(checkpoints));
        for(Checkpoint checkpoint : checkpoints){
            for(Checkpoint child : checkpointMap.get(checkpoint)){
                quest.getQuestGraph().addEdge(checkpoint, child);
            }
        }
        return quest;
    }
}
