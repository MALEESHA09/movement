package com.example.AMS.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets")
public class Asset {
    @Id
    private String assetId;  // Changed from assetId
    
    private String name;
    private String type;
    private String brand;
    private String model;
    private String specification;
    private Date purchaseDate;
    private boolean activityStatus;  
    private String warrantyId;
    private Date warrantyDate;
    private String warrantyPeriod;
    private String purchaseStore;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "asset")
    private List<Condemns> condemns;

    // Getters and Setters
    public String getId() { return assetId; }
    public void setId(String id) { this.assetId = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getSpecification() { return specification; }
    public void setSpecification(String specification) { this.specification = specification; }
    public Date getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(Date purchaseDate) { this.purchaseDate = purchaseDate; }
    public boolean isActive() { return activityStatus; }
    public void setActivityStatus(boolean activityStatus) { this.activityStatus = activityStatus; }
    public String getWarrantyId() { return warrantyId; }
    public void setWarrantyId(String warrantyId) { this.warrantyId = warrantyId; }
    public Date getWarrantyDate() { return warrantyDate; }
    public void setWarrantyDate(Date warrantyDate) { this.warrantyDate = warrantyDate; }
    public String getWarrantyPeriod() { return warrantyPeriod; }
    public void setWarrantyPeriod(String warrantyPeriod) { this.warrantyPeriod = warrantyPeriod; }
    public String getPurchaseStore() { return purchaseStore; }
    public void setPurchaseStore(String purchaseStore) { this.purchaseStore = purchaseStore; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public List<Condemns> getCondemns() { return condemns; }
    public void setCondemns(List<Condemns> condemns) { this.condemns = condemns; }
}