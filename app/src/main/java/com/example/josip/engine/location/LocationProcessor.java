package com.example.josip.engine.location;

import com.example.josip.model.Checkpoint;

import java.util.Set;

/**
 * Created by Josip on 12/10/2014!
 */
public class LocationProcessor {

    private LocationReachedCallback locationReachedCallback;

    public LocationProcessor(LocationReachedCallback locationReachedCallback) {
        this.locationReachedCallback = locationReachedCallback;
    }

    public void trackLocation(Set<Checkpoint> children) {
        //TODO do your yob!
    }
}
