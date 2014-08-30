package com.example.josip.persistance;

import com.example.josip.model.Point;
import com.mysema.query.annotations.QueryProjection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tdubravcevic on 13.8.2014!
 */
@Entity
@Table(name = "quest_details")
public class QuestDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Point point;
    private String description;

    public QuestDetails() {
    }

    @QueryProjection
    public QuestDetails(Long id, String name, Double latitude, Double longitude, String description) {
        this.id = id;
        this.name = name;
        this.point = new Point(latitude, longitude);
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
