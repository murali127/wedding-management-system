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

public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            System.out.println("Unauthorized access - Redirecting to login page");
            response.sendRedirect("login.html");
            return;
        }

        int userId = (int) session.getAttribute("user_id");
        System.out.println("Fetching RSVPs for User ID: " + userId);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT e.name AS eventName, e.date AS eventDate, e.time AS eventTime, e.venue AS eventVenue " +
                          "FROM rsvps r JOIN events e ON r.event_id = e.id WHERE r.user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            List<RSVP> rsvps = new ArrayList<>();
            while (rs.next()) {
                RSVP rsvp = new RSVP();
                rsvp.setEventName(rs.getString("eventName"));
                rsvp.setEventDate(rs.getString("eventDate"));
                rsvp.setEventTime(rs.getString("eventTime"));
                rsvp.setEventVenue(rs.getString("eventVenue"));
                rsvps.add(rsvp);
            }

            // Convert the list of RSVPs to JSON
            StringBuilder json = new StringBuilder("[");
            for (RSVP rsvp : rsvps) {
                if (json.length() > 1) {
                    json.append(",");
                }
                json.append("{\"eventName\":\"").append(rsvp.getEventName()).append("\"")
                    .append(",\"eventDate\":\"").append(rsvp.getEventDate()).append("\"")
                    .append(",\"eventTime\":\"").append(rsvp.getEventTime()).append("\"")
                    .append(",\"eventVenue\":\"").append(rsvp.getEventVenue()).append("\"}");
            }
            json.append("]");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            System.out.println("RSVPs fetched successfully: " + json.toString());
        } catch (Exception e) {
            System.out.println("Exception fetching RSVPs: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching RSVPs");
        }
    }

    // Inner class to represent an RSVP
    private static class RSVP {
        private String eventName;
        private String eventDate;
        private String eventTime;
        private String eventVenue;

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getEventDate() {
            return eventDate;
        }

        public void setEventDate(String eventDate) {
            this.eventDate = eventDate;
        }

        public String getEventTime() {
            return eventTime;
        }

        public void setEventTime(String eventTime) {
            this.eventTime = eventTime;
        }

        public String getEventVenue() {
            return eventVenue;
        }

        public void setEventVenue(String eventVenue) {
            this.eventVenue = eventVenue;
        }
    }
}