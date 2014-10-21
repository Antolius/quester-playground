package com.example.josip.model.graph;

import com.example.josip.model.Checkpoint;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Josip on 10/08/2014!
 */
public class QuestGraph {

    private Map<Checkpoint, Set<Checkpoint>> map;

    /**
     * The only way to make a new Quest Graph is to give it all the checkpoint in advance.
     * It is impossible to add new checkpoints later on.
     *
     * @param checkpoints must not be null nor contain null as an element
     */
    public QuestGraph(Collection<Checkpoint> checkpoints) {
        if (checkpoints == null || checkpoints.contains(null) ) {
            throw new IllegalArgumentException("Collection of checkpoints must not in itself be or contain null.");
        }

        map = new HashMap<Checkpoint, Set<Checkpoint>>(checkpoints.size());
        for (Checkpoint checkpoint : checkpoints) {
            map.put(checkpoint, new HashSet<Checkpoint>());
        }
    }

    /**
     * @return set of all Checkpoints in Quest Graph
     */
    public Set<Checkpoint> getAllCheckpoints() {
        return map.keySet();
    }

    /**
     * @param parent must be already contained in Quest Graph
     * @return Set of children checkpoints
     */
    public Set<Checkpoint> getChildren(Checkpoint parent) {
        assertCheckpointExists(parent);

        return map.get(parent);
    }

    /**
     * @param start must be already contained in Quest Graph
     * @param end must be already contained in Quest Graph
     */
    public void addEdge(Checkpoint start, Checkpoint end) {
        assertCheckpointExists(start);
        assertCheckpointExists(end);

        map.get(start).add(end);
    }

    /**
     * @param start must be already contained in Quest Graph
     * @param end must be already contained in Quest Graph
     */
    public void removeEdge(Checkpoint start, Checkpoint end) {
        assertCheckpointExists(start);
        assertCheckpointExists(end);

        map.get(start).remove(end);
    }


    private void assertCheckpointExists(Checkpoint checkpoint) throws IllegalArgumentException {

        Set<Checkpoint> checkpoints = map.keySet();
        boolean a = checkpoints.contains(checkpoint);
        boolean b = map.containsKey(checkpoint);

        if (!a && !b) {
            throw new IllegalArgumentException("Checkpoint does not exist in quest graph");
        }
    }
}
