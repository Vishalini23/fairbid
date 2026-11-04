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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setContentType("application/json");

        try {
            JsonObject json = JsonParser.parseReader(req.getReader()).getAsJsonObject();
            String action = req.getPathInfo();

            if ("/farmer-register".equals(action)) {
                boolean success = FarmerDAO.register(new Farmer(
                    json.get("farmerId").getAsString(), 
                    json.get("fullName").getAsString(),
                    json.get("state").getAsString(), 
                    json.get("primaryCrop").getAsString(), 
                    json.get("password").getAsString()
                ));
                resp.getWriter().write("{\"success\": " + success + "}");
            } else if ("/farmer-login".equals(action)) {
                boolean success = FarmerDAO.login(
                    json.get("farmerId").getAsString(), 
                    json.get("password").getAsString()
                );
                resp.getWriter().write("{\"success\": " + success + "}");
            } else if ("/buyer-register".equals(action)) {
                boolean success = BuyerDAO.register(new Buyer(
                    json.get("name").getAsString(), 
                    json.get("email").getAsString(),
                    json.get("password").getAsString(), 
                    json.get("category").getAsString(), 
                    json.get("docNumber").getAsString()
                ));
                resp.getWriter().write("{\"success\": " + success + "}");
            } else if ("/buyer-login".equals(action)) {
                boolean success = BuyerDAO.login(
                    json.get("email").getAsString(), 
                    json.get("password").getAsString()
                );
                resp.getWriter().write("{\"success\": " + success + "}");
            } else {
                resp.getWriter().write("{\"success\": false, \"message\": \"Invalid action path\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}