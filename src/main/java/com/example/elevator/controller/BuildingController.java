package com.example.elevator.controller;


import com.example.elevator.entity.Building;
import com.example.elevator.exception.ValidationException;
import com.example.elevator.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/building")
public class BuildingController {

    @Autowired
    private  BuildingService buildingService;


    @GetMapping("/all")
    public List<Building> getAllBuildings() {
        return buildingService.getAllBuildings();
    }

    @GetMapping("/{buildingId}")
    public Building getBuilding(@PathVariable Long buildingId) {
        return buildingService.getBuilding(buildingId);
    }

    @PutMapping("/update")
    public Building updateBuilding(@RequestBody Building building) throws ValidationException {
        return buildingService.updateBuilding(building);
    }
}
