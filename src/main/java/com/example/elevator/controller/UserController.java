package com.example.elevator.controller;

import com.example.elevator.entity.Building;
import com.example.elevator.entity.Elevator;
import com.example.elevator.entity.User;
import com.example.elevator.exception.ValidationException;
import com.example.elevator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) throws ValidationException {
        return userService.updateUser(user);
    }

    @GetMapping("/building/{userId}/")
    public List<Building> findBuildingsForUser(@PathVariable Long userId) throws ValidationException {
        return userService.findBuildingsForUser(userId);
    }

    @GetMapping("/elevator/{userId}")
    public List<Elevator> getElevatorStatusForUser(@PathVariable Long userId) throws ValidationException{
        return userService.getElevatorStatusForUser(userId);
    }
}
