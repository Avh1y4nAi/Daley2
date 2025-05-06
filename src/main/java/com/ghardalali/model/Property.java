package com.ghardalali.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Property model class representing the properties table in the database
 */
public class Property {
    private int propertyId;
    private String propertyName;
    private String propertyType;
    private String description;
    private String location;
    private BigDecimal price;
    private Integer bedrooms;
    private Integer bathrooms;
    private BigDecimal size;
    private String sizeUnit;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<String> imagePaths;
    private String primaryImagePath;
    private int yearBuilt;
    private int garage;
    private String address;
    private String neighborhood;
    private List<String> features;
    private List<String> nearbyPlaces;
    
    // Default constructor
    public Property() {
        this.imagePaths = new ArrayList<>();
        this.features = new ArrayList<>();
        this.nearbyPlaces = new ArrayList<>();
    }
    
    // Constructor with essential fields
    public Property(String propertyName, String propertyType, String location, BigDecimal price, String status) {
        this();
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.location = location;
        this.price = price;
        this.status = status;
    }
    
    // Full constructor
    public Property(int propertyId, String propertyName, String propertyType, String description, String location,
                   BigDecimal price, Integer bedrooms, Integer bathrooms, BigDecimal size, String sizeUnit,
                   String status, Timestamp createdAt, Timestamp updatedAt) {
        this();
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.description = description;
        this.location = location;
        this.price = price;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.size = size;
        this.sizeUnit = sizeUnit;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getPropertyId() {
        return propertyId;
    }
    
    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }
    
    public String getPropertyName() {
        return propertyName;
    }
    
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    public String getPropertyType() {
        return propertyType;
    }
    
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Integer getBedrooms() {
        return bedrooms;
    }
    
    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }
    
    public Integer getBathrooms() {
        return bathrooms;
    }
    
    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }
    
    public BigDecimal getSize() {
        return size;
    }
    
    public void setSize(BigDecimal size) {
        this.size = size;
    }
    
    public String getSizeUnit() {
        return sizeUnit;
    }
    
    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<String> getImagePaths() {
        return imagePaths;
    }
    
    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }
    
    public void addImagePath(String imagePath) {
        this.imagePaths.add(imagePath);
    }
    
    public String getPrimaryImagePath() {
        return primaryImagePath;
    }
    
    public void setPrimaryImagePath(String primaryImagePath) {
        this.primaryImagePath = primaryImagePath;
    }
    
    public int getYearBuilt() {
        return yearBuilt;
    }
    
    public void setYearBuilt(int yearBuilt) {
        this.yearBuilt = yearBuilt;
    }
    
    public int getGarage() {
        return garage;
    }
    
    public void setGarage(int garage) {
        this.garage = garage;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getNeighborhood() {
        return neighborhood;
    }
    
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
    
    public List<String> getFeatures() {
        return features;
    }
    
    public void setFeatures(List<String> features) {
        this.features = features;
    }
    
    public void addFeature(String feature) {
        this.features.add(feature);
    }
    
    public List<String> getNearbyPlaces() {
        return nearbyPlaces;
    }
    
    public void setNearbyPlaces(List<String> nearbyPlaces) {
        this.nearbyPlaces = nearbyPlaces;
    }
    
    public void addNearbyPlace(String nearbyPlace) {
        this.nearbyPlaces.add(nearbyPlace);
    }
    
    @Override
    public String toString() {
        return "Property [propertyId=" + propertyId + ", propertyName=" + propertyName + ", propertyType=" + propertyType
                + ", location=" + location + ", price=" + price + ", status=" + status + "]";
    }
}
