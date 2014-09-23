package com.example.josip.geometry;

import com.example.josip.model.Point;

import java.util.List;

/**
 * Created by tdubravcevic on 15.9.2014!
 */
public class Polygon {

    private List<Line> lines;

    public Polygon() {
    }

    public Polygon(List<Line> lines) {
        this.lines = lines;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public boolean isInside(Point point){
        for(Line line : lines){
            if(!line.isOnSide(point)){
                return false;
            }
        }
        return true;
    }

}
