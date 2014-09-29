package com.example.josip.persistence;

import com.example.josip.model.Checkpoint;
import com.example.josip.model.QuestGraph;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

public class QuestGraphConverter implements Converter<QuestGraph> {

    public QuestGraph read(InputNode node) {
        return null;
    }

    public void write(OutputNode node, QuestGraph external) {

        node.setName("questGraph");
        for(Checkpoint checkpoint : external.getAllCheckpoints()){
            node.setValue();
        }

        node.setAttribute("name", name);
        node.setAttribute("value", value);
    }
}