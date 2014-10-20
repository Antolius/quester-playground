package com.example.josip.model.enums;

import com.example.josip.model.CheckpointArea;
import com.example.josip.model.Circle;
import com.example.josip.model.Point;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tdubravcevic on 13.10.2014!
 */
public class CircleArea implements CheckpointArea {

    private Circle circle;

    public CircleArea(Circle circle) {
        this.circle = circle;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    @Override
    public boolean isInside(Point point) {
        return true;
    }

    @Override
    public double distanceFrom(Point point, MeasurementUnit measurementUnit) {
        return 0;
    }

    @Override
    public Circle aproximatingCircle() {

        return circle;
    }

    @Override
    public JSONObject getJsonData() throws JSONException {

        return new JSONObject()
                .put("radius", circle.getRadius())
                .put("latitude", circle.getCenter().getLatitude())
                .put("longitude", circle.getCenter().getLongitude());
    }

    public static class CircleAreaBuilder {

        private Circle circle;
        private Point center;

        public CircleAreaBuilder() {
            this.circle = new Circle();
            this.center = new Point(0.0, 0.0);
        }

        public CircleAreaBuilder centerLatitude(Double centerLatitude) {
            center.setLatitude(centerLatitude);
            return this;
        }

        public CircleAreaBuilder centerLongitude(Double centerLongitude) {
            center.setLongitude(centerLongitude);
            return this;
        }

        public CircleAreaBuilder radius(Double radius){
            circle.setRadius(radius);
            return this;
        }

        public CircleArea build() {
            circle.setCenter(center);
            return new CircleArea(circle);
        }
    }
}
