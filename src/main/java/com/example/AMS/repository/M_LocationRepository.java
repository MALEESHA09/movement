package com.example.AMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AMS.model.Location;

@Repository
public interface M_LocationRepository extends JpaRepository<Location, Long> {
    // Add any custom queries you need for locations
    boolean existsById(Long id);
}