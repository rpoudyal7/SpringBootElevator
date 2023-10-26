package com.example.elevator.controller;


import com.example.elevator.entity.Elevator;
import com.example.elevator.exception.ValidationException;
import com.example.elevator.service.ElevatorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elevator")
public class ElevatorController {
    private static final Logger logger = LogManager.getLogger(ElevatorController.class);

    @Autowired
    private ElevatorService elevatorService;


    @GetMapping("/all")
    public List<Elevator> getAllElevators() {
        return elevatorService.getAllElevators();
    }

    @PostMapping("/user/{userId}/building/{buildingId}/floor/{floorId}")
    public Elevator summonElevator(@PathVariable("userId") Long userId, @PathVariable("buildingId") Long buildingId,  @PathVariable("floorId") Integer floorId) throws ValidationException {
        return elevatorService.summonElevator(userId, buildingId, floorId);
    }

    @PutMapping("/{elevatorId}/floor/{floor}")
    public Elevator selectFloor(@PathVariable Long elevatorId, @PathVariable Integer floor) throws ValidationException {
        logger.info("elevatorId: " + elevatorId);
        return elevatorService.selectFloor(elevatorId, floor);
    }
}
