package com.example.elevator.service;


import com.example.elevator.entity.Building;
import com.example.elevator.entity.Elevator;
import com.example.elevator.entity.User;
import com.example.elevator.exception.ValidationException;
import com.example.elevator.repository.BuildingRepository;
import com.example.elevator.repository.ElevatorRepository;
import com.example.elevator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private ElevatorRepository elevatorRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User requestUser) throws ValidationException {

        if (requestUser.getId() == null) {
            throw new ValidationException("User Id is null");
        }

        Optional<User> userOptional = userRepository.findById(requestUser.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(requestUser.getName());
            user.setBuildingIds(requestUser.getBuildingIds());
            return userRepository.save(user);

        }
        return null;
    }

    public List<Building> findBuildingsForUser(Long userId) throws ValidationException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new ValidationException("User does not exist");
        }

        User user = userOptional.get();
        List<Long> buildingIds = user.getBuildingIds();
        return buildingRepository.findAllById(buildingIds);
    }

    public List<Elevator> getElevatorStatusForUser(Long userId) throws ValidationException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new ValidationException("User does not exist");
        }
        User user = userOptional.get();
        List<Long> buildingIds = user.getBuildingIds();
        return elevatorRepository.findByBuildingIdIn(buildingIds);
    }
}

