package com.dao;
import com.model.Farmer;
import com.util.DBConnection;
import java.sql.*;

public class FarmerDAO {
    public static boolean register(Farmer f) {
        String sql = "INSERT INTO farmers (farmer_id, full_name, state, primary_crop, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getFarmerId()); ps.setString(2, f.getFullName()); ps.setString(3, f.getState());
            ps.setString(4, f.getPrimaryCrop()); ps.setString(5, f.getPassword());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    public static boolean login(String id, String pwd) {
        String sql = "SELECT * FROM farmers WHERE farmer_id = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id); ps.setString(2, pwd);
            return ps.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}