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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ElevatorServiceTest {
    private ElevatorService elevatorService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private ElevatorRepository elevatorRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        elevatorService = new ElevatorService(elevatorRepository, buildingRepository, userRepository);
    }

    @Test
    public void testSummonElevator() throws ValidationException {
        // Arrange
        Long userId = 1L;
        Long buildingId = 2L;
        Integer floor = 3;

        User user = new User();
        user.setBuildingIds(Collections.singletonList(buildingId));

        Building building = new Building();
        building.setElevatorIds(Arrays.asList(1L, 2L));

        Elevator elevator1 = new Elevator();
        elevator1.setId(1L);
        elevator1.setState(ElevatorState.STOPPED);
        elevator1.setCurrentFloor(4);

        Elevator elevator2 = new Elevator();
        elevator2.setId(2L);
        elevator2.setState(ElevatorState.STOPPED);
        elevator2.setCurrentFloor(5);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));
        when(elevatorRepository.findAllById(building.getElevatorIds())).thenReturn(Arrays.asList(elevator1, elevator2));

        // Act
        Elevator result = elevatorService.summonElevator(userId, buildingId, floor);

        // Assert
        assertEquals(floor, result.getCurrentFloor());
        verify(elevatorRepository, times(1)).save(result);
    }
    @Test
    public void testSelectInvalidFloor() {
        Long elevatorId = 3L;
        Integer floor = 10;

        Elevator initialElevator = new Elevator(3L, "Elevator C", createFloors(), 1, ElevatorState.STOPPED);

        when(elevatorRepository.findById(elevatorId)).thenReturn(Optional.of(initialElevator));

        Elevator updatedElevator = elevatorService.selectFloor(elevatorId, floor);

        assertEquals(null, updatedElevator);

        verify(elevatorRepository, times(1)).findById(elevatorId);
    }
    private List<Integer> createFloors() {
        List<Integer> floors = new ArrayList<>();
        floors.add(1);
        floors.add(2);
        floors.add(3);
        floors.add(4);
        return floors;
    }
}
