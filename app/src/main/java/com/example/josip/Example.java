package com.example.josip;

import com.mysema.query.sql.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tdubravcevic on 11.8.2014!
 */
@Entity
@Table(name = "Example")
public class Example {

    @Id
    @GeneratedValue
    @Column("example_id")
    private Integer id;

    @Column("name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
