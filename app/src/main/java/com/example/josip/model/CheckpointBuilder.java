package com.example.josip.model;

import java.io.File;

/**
 * Created by tdubravcevic on 13.10.2014!
 */
public class CheckpointBuilder {

    private Checkpoint checkpoint;

    public CheckpointBuilder(Long id){
        checkpoint = new Checkpoint();
        checkpoint.setId(id);
    }

    public CheckpointBuilder(String name){
        checkpoint = new Checkpoint();
        checkpoint.setName(name);
    }

    public CheckpointBuilder id(Long id){
        checkpoint.setId(id);
        return this;
    }

    public CheckpointBuilder name(String name){
        checkpoint.setName(name);
        return this;
    }

    public CheckpointBuilder isRoot(boolean isRoot) {
        checkpoint.setRoot(isRoot);
        return this;
    }

    public CheckpointBuilder area(CheckpointArea area){
        checkpoint.setArea(area);
        return this;
    }

    public  CheckpointBuilder viewHtml(File viewHtml) {
        checkpoint.setViewHtml(viewHtml);
        return this;
    }

    public CheckpointBuilder eventScript(File eventScript) {
        checkpoint.setEventsScript(eventScript);
        return this;
    }

    public Checkpoint build(){
        return checkpoint;
    }
}
