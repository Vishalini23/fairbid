package com.model;
public class Crop {
    private String farmerId, cropName, location;
    private int quantity;
    private double minPrice;
    public Crop(String farmerId, String cropName, int quantity, double minPrice, String location) {
        this.farmerId = farmerId; this.cropName = cropName; this.quantity = quantity; this.minPrice = minPrice; this.location = location;
    }
    public String getFarmerId() { return farmerId; }
    public String getCropName() { return cropName; }
    public int getQuantity() { return quantity; }
    public double getMinPrice() { return minPrice; }
    public String getLocation() { return location; }
}