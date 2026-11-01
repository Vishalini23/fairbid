package com.controller;
import com.model.Crop;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/crops")
public class CropServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        JsonObject json = JsonParser.parseReader(req.getReader()).getAsJsonObject();

        boolean success = saveCrop(new Crop(
            json.get("farmerId").getAsString(), json.get("cropName").getAsString(),
            json.get("quantity").getAsInt(), json.get("minPrice").getAsDouble(), json.get("location").getAsString()
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

    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}