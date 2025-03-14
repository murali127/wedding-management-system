package com.wedding.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.wedding.utils.DatabaseConnection;

public class EventServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            System.out.println("Unauthorized access - Redirecting to login page");
            response.sendRedirect("login.html");
            return;
        }

        int userId = (int) session.getAttribute("user_id");
        String weddingId = request.getParameter("wedding_id");

        if (weddingId == null || weddingId.trim().isEmpty()) {
            System.out.println("Wedding ID not provided");
            response.sendRedirect("dashboard.html?error=1");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Database connection established");

            // SQL query to fetch events with RSVP status
            String sql = "SELECT e.id AS event_id, e.name, e.date, e.time, e.venue, " +
                         "CASE WHEN r.user_id IS NOT NULL THEN TRUE ELSE FALSE END AS rsvped " +
                         "FROM events e " +
                         "LEFT JOIN rsvps r ON e.id = r.event_id AND r.user_id = ? " +
                         "WHERE e.wedding_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId); // Current user's ID
            pstmt.setInt(2, Integer.parseInt(weddingId)); // Wedding ID
            ResultSet rs = pstmt.executeQuery();

            List<Event> events = new ArrayList<>();
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("event_id"));
                event.setName(rs.getString("name"));
                event.setDate(rs.getString("date"));
                event.setTime(rs.getString("time"));
                event.setVenue(rs.getString("venue"));
                event.setRsvped(rs.getBoolean("rsvped")); // Set the RSVP status
                events.add(event);
            }

            // Convert events list to JSON
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                if (i > 0) {
                    json.append(",");
                }
                json.append("{\"id\":").append(event.getId())
                    .append(",\"name\":\"").append(event.getName()).append("\"")
                    .append(",\"date\":\"").append(event.getDate()).append("\"")
                    .append(",\"time\":\"").append(event.getTime()).append("\"")
                    .append(",\"venue\":\"").append(event.getVenue()).append("\"")
                    .append(",\"rsvped\":").append(event.isRsvped()).append("}");
            }
            json.append("]");

            // Send JSON response to the frontend
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            System.out.println("Exception fetching events: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("dashboard.html?error=1");
        }
    }

    // Inner class to represent an event
    private static class Event {
        private int id;
        private String name;
        private String date;
        private String time;
        private String venue;
        private boolean rsvped; // Add this field

        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getVenue() {
            return venue;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }

        // Add getter and setter for rsvped
        public boolean isRsvped() {
            return rsvped;
        }

        public void setRsvped(boolean rsvped) {
            this.rsvped = rsvped;
        }
    }
}