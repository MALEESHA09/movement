package com.example.AMS.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")  // explicit column name
    private Long locationId; 
    private Date transferDate;
    private String departmentName;
    private Date startDate;
    private Date endDate;

    @OneToMany(mappedBy = "location")
    private List<Asset> assets;

    @OneToMany(mappedBy = "location")
    private List<Room> rooms;

    @OneToMany(mappedBy = "location")
    private List<Condemns> condemns;

    // Getters and Setters
    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }
    public Date getTransferDate() { return transferDate; }
    public void setTransferDate(Date transferDate) { this.transferDate = transferDate; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public List<Asset> getAssets() { return assets; }
    public void setAssets(List<Asset> assets) { this.assets = assets; }
    public List<Room> getRooms() { return rooms; }
    public void setRooms(List<Room> rooms) { this.rooms = rooms; }
    public List<Condemns> getCondemns() { return condemns; }
    public void setCondemns(List<Condemns> condemns) { this.condemns = condemns; }

     // Fixed query method
    public static List<Asset> findAssetsByLocationId(EntityManager em, Long locationId) {
        return em.createQuery("SELECT a FROM Asset a WHERE a.location.locationId = :locationId", Asset.class)
                 .setParameter("locationId", locationId)
                 .getResultList();
    }

}