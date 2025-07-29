package com.example.AMS.service;

import com.example.AMS.model.Location;
import com.example.AMS.repository.M_MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class M_MovementService {

    private final M_MovementRepository movementRepository;

    @Autowired
    public M_MovementService(M_MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    // Record a new movement (location transfer)
    public Location recordMovement(Location location) {
        // Set the transfer date to now if not provided
        if (location.getTransferDate() == null) {
            location.setTransferDate(new Date());
        }
        return movementRepository.save(location);
    }

    // Update a movement (location transfer)
    public Location updateMovement(String locationId, Location locationDetails) {
        Location location = movementRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Movement not found with id: " + locationId));
        
        location.setDepartmentName(locationDetails.getDepartmentName());
        location.setTransferDate(locationDetails.getTransferDate());
        location.setStartDate(locationDetails.getStartDate());
        location.setEndDate(locationDetails.getEndDate());
        
        return movementRepository.save(location);
    }

    // Get all movements
    public List<Location> getAllMovements() {
        return movementRepository.findAll();
    }

    // Get movements by date range
    public List<Location> getMovementsByDateRange(Date startDate, Date endDate) {
        return movementRepository.findByTransferDateBetween(startDate, endDate);
    }

    // Get current active movements
    public List<Location> getActiveMovements() {
        return movementRepository.findActiveLocations();
    }

    // Get movement by ID
    public Location getMovementById(String locationId) {
        return movementRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Movement not found with id: " + locationId));
    }

    // Delete a movement
    public void deleteMovement(String locationId) {
        Location location = movementRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Movement not found with id: " + locationId));
        movementRepository.delete(location);
    }

    // Search movements by department name
    public List<Location> searchMovementsByDepartment(String departmentName) {
        return movementRepository.findByDepartmentNameContainingIgnoreCase(departmentName);
    }

    // Get locations with assets (representing movements with assets)
    public List<Location> getMovementsWithAssets() {
        return movementRepository.findLocationsWithAssets();
    }
}