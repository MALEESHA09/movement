package com.example.AMS.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.AMS.model.Location;

@Repository
public interface M_MovementRepository extends JpaRepository<Location, String> {
    
    // Find locations by transfer date range
    List<Location> findByTransferDateBetween(Date startDate, Date endDate);
    
    // Find active locations (where endDate is null or in future)
    @Query("SELECT l FROM Location l WHERE l.endDate IS NULL OR l.endDate > CURRENT_DATE")
    List<Location> findActiveLocations();
    
    // Find locations by department name
    List<Location> findByDepartmentNameContainingIgnoreCase(String departmentName);
    
    // Find locations with assets (assuming you want to track movements with assets)
    @Query("SELECT DISTINCT l FROM Location l JOIN l.assets a")
    List<Location> findLocationsWithAssets();
}