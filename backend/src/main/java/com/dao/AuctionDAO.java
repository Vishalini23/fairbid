package com.dao;

import com.util.DBConnection;
import java.sql.*;

public class AuctionDAO {

    // Method to place a new bid on a crop
    public static boolean placeBid(int cropId, String buyerEmail, double bidAmount) {
        String sql = "INSERT INTO bids (crop_id, buyer_email, bid_amount) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, cropId);
            ps.setString(2, buyerEmail);
            ps.setDouble(3, bidAmount);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}