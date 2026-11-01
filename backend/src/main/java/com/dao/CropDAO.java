package com.dao;
import com.model.Crop;
import com.util.DBConnection;
import java.sql.*;

public class CropDAO {
    public static boolean saveCrop(Crop c) {
        String sql = "INSERT INTO crops (farmer_id, crop_name, quantity, min_price, location) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getFarmerId()); ps.setString(2, c.getCropName()); ps.setInt(3, c.getQuantity());
            ps.setDouble(4, c.getMinPrice()); ps.setString(5, c.getLocation());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}