package com.example.elevator.service;


import com.example.elevator.entity.Building;
import com.example.elevator.entity.Elevator;
import com.example.elevator.entity.User;
import com.example.elevator.exception.ValidationException;
import com.example.elevator.model.ElevatorState;
import com.example.elevator.repository.BuildingRepository;
import com.example.elevator.repository.ElevatorRepository;
import com.example.elevator.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElevatorService {

    private static final Logger logger = LogManager.getLogger(ElevatorService.class);

    private final ElevatorRepository elevatorRepository;
    private final BuildingRepository buildingRepository;
    private final UserRepository userRepository;

    @Autowired
    public ElevatorService(ElevatorRepository elevatorRepository, BuildingRepository buildingRepository, UserRepository userRepository) {
        this.elevatorRepository = elevatorRepository;
        this.buildingRepository = buildingRepository;
        this.userRepository = userRepository;
    }

    public List<Elevator> getAllElevators() {
        logger.info("Get All Elevators");
        return elevatorRepository.findAll();
    }


    public Elevator summonElevator(Long userId, Long buildingId, Integer floor) throws ValidationException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()){
            throw new ValidationException("User does not exist");
        }

        User user = optionalUser.get();
        List<Long> buildingIds = user.getBuildingIds();
        if(!buildingIds.contains(buildingId)){
            throw new ValidationException("User not authorized in building");
        }

        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if(!optionalBuilding.isPresent()){
            throw new ValidationException("building does not exist");
        }

        Building building = optionalBuilding.get();
        List<Long> elevatorIds = building.getElevatorIds();
        List<Elevator> elevators = elevatorRepository.findAllById(elevatorIds);
        List<Elevator> elevatorsStopped = elevators.stream().filter(elevator -> elevator.getState() == ElevatorState.STOPPED).collect(Collectors.toList());
        Elevator closestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevatorsStopped) {
            int distance = Math.abs(elevator.getCurrentFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                closestElevator = elevator;
            }
        }

        closestElevator.setCurrentFloor(floor);
        elevatorRepository.save(closestElevator);
        return closestElevator;
    }


    public Elevator selectFloor(Long elevatorId, Integer floor) {
        Optional<Elevator> elevatorOptional = elevatorRepository.findById(elevatorId);
        if (elevatorOptional.isPresent()) {
            Elevator elevator = elevatorOptional.get();
            if(elevator.getCurrentFloor() < floor){
                elevator.setState(ElevatorState.UP);
            }else{
                elevator.setState(ElevatorState.DOWN);
            }
            List<Integer> floors = elevator.getFloors();
            if (floors.contains(floor)) {
                elevator.setCurrentFloor(floor);
                return elevatorRepository.save(elevator);
            }
        }
        return null;
    }
}

