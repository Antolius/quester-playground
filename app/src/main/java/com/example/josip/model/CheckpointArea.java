package com.example.josip.model;

import com.example.josip.model.enums.MessurmentUnit;

/**
 * Created by Josip on 10/08/2014.
 */
public interface CheckpointArea {

    public boolean isInside(Point point);

    public double distanceFrom(Point point, MessurmentUnit messurmentUnit);

    public Circle aproximatingCircle();

}