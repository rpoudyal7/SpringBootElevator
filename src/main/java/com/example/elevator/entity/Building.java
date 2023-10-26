package com.example.elevator.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;

    @ElementCollection
    private List<Long> elevatorIds;


    public Building() {
    }

    public Building(Long id, String name, String location, List<Long> elevatorIds) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.elevatorIds = elevatorIds;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Long> getElevatorIds() {
        return elevatorIds;
    }

    public void setElevatorIds(List<Long> elevatorIds) {
        this.elevatorIds = elevatorIds;
    }
}
