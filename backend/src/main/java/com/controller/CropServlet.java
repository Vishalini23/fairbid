package com.controller;

import com.model.Crop;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/crops")
public class CropServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setContentType("application/json");

        JsonObject json = JsonParser.parseReader(req.getReader()).getAsJsonObject();

        boolean success = saveCrop(new Crop(
            json.get("farmerId").getAsString(), 
            json.get("cropName").getAsString(),
            json.get("quantity").getAsInt(), 
            json.get("minPrice").getAsDouble(), 
            json.get("location").getAsString()
        ));
        resp.getWriter().write("{\"success\": " + success + "}");
    }

    private boolean saveCrop(Crop crop) {
        try {
            Class<?> daoClass = Class.forName("com.dao.CropDAO");
            try {
                java.lang.reflect.Method saveMethod = daoClass.getMethod("saveCrop", Crop.class);
                return (boolean) saveMethod.invoke(null, crop);
            } catch (NoSuchMethodException e) {
                Object daoInstance = daoClass.getDeclaredConstructor().newInstance();
                java.lang.reflect.Method saveMethod = daoClass.getMethod("saveCrop", Crop.class);
                return (boolean) saveMethod.invoke(daoInstance, crop);
            }
        } catch (ReflectiveOperationException e) {
            return false;
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