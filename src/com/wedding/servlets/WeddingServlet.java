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
import com.wedding.utils.DatabaseConnection;

public class WeddingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("WeddingServlet - Fetching weddings...");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM weddings";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            List<Wedding> weddings = new ArrayList<>();
            while (rs.next()) {
                Wedding wedding = new Wedding();
                wedding.setId(rs.getInt("id"));
                wedding.setName(rs.getString("name"));
                wedding.setDate(rs.getString("date"));
                wedding.setTime(rs.getString("time"));
                wedding.setVenue(rs.getString("venue"));
                weddings.add(wedding);
            }

            // Convert the list of weddings to JSON
            StringBuilder json = new StringBuilder("[");
            for (Wedding wedding : weddings) {
                if (json.length() > 1) {
                    json.append(",");
                }
                json.append("{\"id\":").append(wedding.getId())
                    .append(",\"name\":\"").append(wedding.getName()).append("\"")
                    .append(",\"date\":\"").append(wedding.getDate()).append("\"")
                    .append(",\"time\":\"").append(wedding.getTime()).append("\"")
                    .append(",\"venue\":\"").append(wedding.getVenue()).append("\"}");
            }
            json.append("]");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            System.out.println("Weddings fetched successfully: " + json.toString());
        } catch (Exception e) {
            System.out.println("Exception fetching weddings: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching weddings");
        }
    }

    // Inner class to represent a wedding
    private static class Wedding {
        private int id;
        private String name;
        private String date;
        private String time;
        private String venue;

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
    }
}