package com.dao;
import com.model.Buyer;
import com.util.DBConnection;
import java.sql.*;

public class BuyerDAO {
    public static boolean register(Buyer b) {
        String sql = "INSERT INTO buyers (name, email, password, category, doc_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getName()); ps.setString(2, b.getEmail()); ps.setString(3, b.getPassword());
            ps.setString(4, b.getCategory()); ps.setString(5, b.getDocNumber());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    public static boolean login(String email, String pwd) {
        String sql = "SELECT * FROM buyers WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email); ps.setString(2, pwd);
            return ps.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}