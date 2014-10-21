package com.example.josip.model;

import com.example.josip.model.graph.QuestGraph;

import java.util.LinkedList;
import java.util.List;

public class QuestState {

    private QuestGraph questGraph;
    private List<Checkpoint> visitedCheckpoints;

    public QuestState() {
    }

    public QuestState(QuestGraph questGraph) {
        this.questGraph = questGraph;
        this.visitedCheckpoints = new LinkedList<Checkpoint>();
    }

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
