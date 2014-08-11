package com.example.josip.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Josip on 10/08/2014.
 */
public class QuestState {

    private QuestGraph questGraph;
    private List<Checkpoint> visitedCheckpoints;

    public QuestGraph getQuestGraph() {
        return questGraph;
    }

    public void setQuestGraph(QuestGraph questGraph) {
        this.questGraph = questGraph;
    }

    public List<Checkpoint> getVisitedCheckpoints() {
        return visitedCheckpoints;
    }

    public void setVisitedCheckpoints(List<Checkpoint> visitedCheckpoints) {
        this.visitedCheckpoints = visitedCheckpoints;
    }

    public Checkpoint getActiveCheckpoint () {
        return visitedCheckpoints.get(visitedCheckpoints.size() - 1);
    }
}
