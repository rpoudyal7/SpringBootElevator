package com.example.elevator.service;

import com.example.elevator.entity.Building;
import com.example.elevator.exception.ValidationException;
import com.example.elevator.repository.BuildingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BuildingServiceTest {

    @InjectMocks
    private BuildingService buildingService;

    @Mock
    private BuildingRepository buildingRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateBuildingNonExistentBuilding() {
        Building updatedBuilding = new Building(2L, "New Building", "New Location", null);

        when(buildingRepository.findById(2L)).thenReturn(Optional.empty());
        try {
            buildingService.updateBuilding(updatedBuilding);
        } catch (ValidationException e) {
            assertEquals("Building does not exist", e.getMessage());
        }
        verify(buildingRepository, times(1)).findById(2L);
    }
}
