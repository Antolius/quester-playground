package com.example.josip.model.graph;

import com.example.josip.model.Checkpoint;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Josip on 18/10/2014!
 */
public class QuestGraphUtils {

    public static Set<Checkpoint> getRootCheckpoints(QuestGraph graph) {

        Set<Checkpoint> result = new HashSet<Checkpoint>();

        for (Checkpoint checkpoint : graph.getAllCheckpoints()) {
            if (checkpoint.isRoot()) {
                result.add(checkpoint);
            }
        }

        return result;

    }

}
