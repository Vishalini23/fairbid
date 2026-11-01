package com.controller;
import com.dao.BuyerDAO;
import com.dao.FarmerDAO;
import com.model.Buyer;
import com.model.Farmer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/auth/*")
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        JsonObject json = JsonParser.parseReader(req.getReader()).getAsJsonObject();
        String action = req.getPathInfo();

        if ("/farmer-register".equals(action)) {
            boolean success = FarmerDAO.register(new Farmer(
                json.get("farmerId").getAsString(), json.get("fullName").getAsString(),
                json.get("state").getAsString(), json.get("primaryCrop").getAsString(), json.get("password").getAsString()
            ));
            resp.getWriter().write("{\"success\": " + success + "}");
        } else if ("/farmer-login".equals(action)) {
            boolean success = FarmerDAO.login(json.get("farmerId").getAsString(), json.get("password").getAsString());
            resp.getWriter().write("{\"success\": " + success + "}");
        } else if ("/buyer-register".equals(action)) {
            boolean success = BuyerDAO.register(new Buyer(
                json.get("name").getAsString(), json.get("email").getAsString(),
                json.get("password").getAsString(), json.get("category").getAsString(), json.get("docNumber").getAsString()
            ));
            resp.getWriter().write("{\"success\": " + success + "}");
        } else if ("/buyer-login".equals(action)) {
            boolean success = BuyerDAO.login(json.get("email").getAsString(), json.get("password").getAsString());
            resp.getWriter().write("{\"success\": " + success + "}");
        }
    }
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}