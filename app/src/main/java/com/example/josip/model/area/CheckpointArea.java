package com.example.josip.model.area;

import com.example.josip.model.Point;
import com.example.josip.model.enums.MeasurementUnit;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Josip on 10/08/2014.
 */
public interface CheckpointArea {

    public boolean isInside(Point point);

    public double distanceFrom(Point point, MeasurementUnit messurmentUnit);

    public Circle aproximatingCircle();

    public JSONObject getJsonData() throws JSONException;
}