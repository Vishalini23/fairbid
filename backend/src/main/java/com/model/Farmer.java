package com.model;
public class Farmer {
    private String farmerId, fullName, state, primaryCrop, password;
    public Farmer(String farmerId, String fullName, String state, String primaryCrop, String password) {
        this.farmerId = farmerId; this.fullName = fullName; this.state = state; this.primaryCrop = primaryCrop; this.password = password;
    }
    public String getFarmerId() { return farmerId; }
    public String getFullName() { return fullName; }
    public String getState() { return state; }
    public String getPrimaryCrop() { return primaryCrop; }
    public String getPassword() { return password; }
}