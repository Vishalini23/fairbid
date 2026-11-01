package com.model;

public class Bid {
    private int bidId;
    private int cropId;
    private String buyerEmail;
    private double bidAmount;

    public Bid(int cropId, String buyerEmail, double bidAmount) {
        this.cropId = cropId;
        this.buyerEmail = buyerEmail;
        this.bidAmount = bidAmount;
    }

    public int getBidId() {
        return bidId;
    }

    public int getCropId() {
        return cropId;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public double getBidAmount() {
        return bidAmount;
    }
}