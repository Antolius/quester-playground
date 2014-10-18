package com.example.josip.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Josip on 18/10/2014!
 */
public class QuestGraphUtils {

    public static Set<Checkpoint> getRotCheckpoints(QuestGraph graph) {

        Set<Checkpoint> result = new HashSet<Checkpoint>();

        for (Checkpoint checkpoint : graph.getAllCheckpoints()) {
            if (checkpoint.isRoot()) {
                result.add(checkpoint);
            }
        }

        return result;

    }

}
