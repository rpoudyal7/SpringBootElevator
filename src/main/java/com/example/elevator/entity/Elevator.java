package com.example.elevator.entity;

import com.example.elevator.model.ElevatorState;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Elevator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ElementCollection
    private List<Integer> floors;
    private Integer currentFloor;

    @Enumerated(EnumType.STRING)
    private ElevatorState state;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Elevator(Long id, String name, List<Integer> floors, Integer currentFloor, ElevatorState state) {
        this.id = id;
        this.name = name;
        this.floors = floors;
        this.currentFloor = currentFloor;
        this.state = state;
    }

    public Elevator() {

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

    public List<Integer> getFloors() {
        return floors;
    }

    public void setFloors(List<Integer> floors) {
        this.floors = floors;
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(Integer currentFloor) {
        this.currentFloor = currentFloor;
    }

    public ElevatorState getState() {
        return state;
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }
}
