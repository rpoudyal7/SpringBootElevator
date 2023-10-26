package com.example.elevator.service;

import com.example.elevator.entity.Building;
import com.example.elevator.entity.Elevator;
import com.example.elevator.entity.User;
import com.example.elevator.exception.ValidationException;
import com.example.elevator.model.ElevatorState;
import com.example.elevator.repository.BuildingRepository;
import com.example.elevator.repository.ElevatorRepository;
import com.example.elevator.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @InjectMocks
    private ElevatorService elevatorService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BuildingRepository buildingRepository;
    @Mock
    private ElevatorRepository elevatorRepository;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testFindBuildingsForUser() throws ValidationException {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setBuildingIds(List.of(1L, 2L, 3L));

        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(1L, "Building A", "Location A", List.of(1L, 2L, 3L)));
        buildings.add(new Building(2L, "Building B", "Location B", List.of(4L, 5L)));
        buildings.add(new Building(3L, "Building C", "Location C", List.of(6L)));

        // Define the behavior of the mock repository
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(buildingRepository.findAllById(user.getBuildingIds())).thenReturn(buildings);

        // Act
        List<Building> result = userService.findBuildingsForUser(userId);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size()); // Ensure that all buildings are returned
    }
    @Test
    public void testGetElevatorStatusForUser() throws ValidationException {
        // Given
        Long userId = 1L;
        List<Long> buildingIds = new ArrayList<>();
        buildingIds.add(1L);
        buildingIds.add(2L);

        // Mock the repository to return a user when findById is called
        User user = new User(1L, "John Doe", buildingIds);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Mock the repository to return elevators for the user's building IDs
        List<Elevator> expectedElevators = new ArrayList<>();
        expectedElevators.add(new Elevator(1L, "Elevator A", Arrays.asList(1), 0, ElevatorState.STOPPED));
        expectedElevators.add(new Elevator(2L, "Elevator B", Arrays.asList(2), 0, ElevatorState.STOPPED));
        when(elevatorRepository.findByBuildingIdIn(buildingIds)).thenReturn(expectedElevators);

        // When
        List<Elevator> result = userService.getElevatorStatusForUser(userId);

        // Then
        assertEquals(expectedElevators, result);

        // Verify that findById and findByBuildingIdIn methods were called
        verify(userRepository, times(1)).findById(userId);
        verify(elevatorRepository, times(1)).findByBuildingIdIn(buildingIds);
    }
}
