package com.example.josip.geometry;

import com.example.josip.model.Point;

/**
 * Created by tdubravcevic on 15.9.2014!
 */
public class Line {

    private Point start;
    private Point end;

    public Line() {
    }

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public boolean isOnSide(Point point) {

        double k = (end.getLongitude() - start.getLongitude()) / (end.getLatitude() - start.getLatitude());
        double l = k * start.getLatitude() + start.getLongitude();

        return k * point.getLatitude() + point.getLongitude() + l < 0;
    }
}
