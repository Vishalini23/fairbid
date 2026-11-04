package com.controller;

import com.dao.AuctionDAO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/bids")
public class AuctionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setContentType("application/json");
        
        try {
            JsonObject json = JsonParser.parseReader(req.getReader()).getAsJsonObject();
            
            int cropId = json.get("cropId").getAsInt();
            String buyerEmail = json.get("buyerEmail").getAsString();
            double bidAmount = json.get("bidAmount").getAsDouble();

            boolean success = AuctionDAO.placeBid(cropId, buyerEmail, bidAmount);
            resp.getWriter().write("{\"success\": " + success + "}");
            
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"success\": false}");
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