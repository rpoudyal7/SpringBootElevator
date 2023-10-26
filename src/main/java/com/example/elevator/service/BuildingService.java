package com.example.elevator.service;


import com.example.elevator.entity.Building;
import com.example.elevator.exception.ValidationException;
import com.example.elevator.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Building getBuilding(Long buildingId) {
        return buildingRepository.findById(buildingId).orElse(null);
    }

    public Building updateBuilding( Building updateBuilding) throws ValidationException {
        Optional<Building> buildingOptional = buildingRepository.findById(updateBuilding.getId());

        if (!buildingOptional.isPresent()) {
            throw new ValidationException("Building does not exist");
        }

        Building building = buildingOptional.get();
        building.setLocation(updateBuilding.getLocation());
        building.setName(updateBuilding.getName());
        building.setElevatorIds(updateBuilding.getElevatorIds());
        return buildingRepository.save(building);
    }
}

