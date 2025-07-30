package com.example.AMS.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.AMS.model.Location;

@Repository
public interface M_MovementRepository extends JpaRepository<Location, Long> {
    
    // Find locations by transfer date range
    List<Location> findByTransferDateBetween(Date startDate, Date endDate);
    
    // Find active locations (where endDate is null or in future)
    @Query("SELECT l FROM Location l WHERE l.endDate IS NULL OR l.endDate > CURRENT_DATE")
    List<Location> findActiveLocations();
    
    // Find locations by department name
    List<Location> findByDepartmentNameContainingIgnoreCase(String departmentName);
    
    // Find locations with assets
    @Query("SELECT DISTINCT l FROM Location l JOIN FETCH l.assets")
    List<Location> findLocationsWithAssets();
    
    // Find movement history for a specific asset
    @Query("SELECT l FROM Location l JOIN l.assets a WHERE a.id = :assetId ORDER BY l.transferDate DESC")
    List<Location> findByAssetId(Long assetId);
}