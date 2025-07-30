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

    // Record a new movement
    public Location recordMovement(Location location) {
        if (location.getTransferDate() == null) {
            location.setTransferDate(new Date());
        }
        return movementRepository.save(location);
    }

    // Update a movement
    public Location updateMovement(Long id, Location locationDetails) {
        Location location = movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
        
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

    // Get active movements
    public List<Location> getActiveMovements() {
        return movementRepository.findActiveLocations();
    }

    // Get movement by ID
    public Location getMovementById(Long id) {
        return movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
    }

    // Delete a movement
    public void deleteMovement(Long id) {
        Location location = movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
        movementRepository.delete(location);
    }

    // Search by department name
    public List<Location> searchByDepartment(String departmentName) {
        return movementRepository.findByDepartmentNameContainingIgnoreCase(departmentName);
    }

    // Get movements with assets
    public List<Location> getMovementsWithAssets() {
        return movementRepository.findLocationsWithAssets();
    }

    // Get movement history for an asset
    public List<Location> getMovementHistoryForAsset(Long assetId) {
        return movementRepository.findByAssetId(assetId);
    }
}