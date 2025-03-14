package com.wedding.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.wedding.utils.DatabaseConnection;

public class RSVPServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            System.out.println("Unauthorized access - Redirecting to login page");
            response.sendRedirect("login.html");
            return;
        }

        int userId = (int) session.getAttribute("user_id");
        String action = request.getParameter("action");
        System.out.println("RSVPServlet - Action: " + action);

        if ("rsvp_event".equals(action)) {
            int eventId = Integer.parseInt(request.getParameter("event_id"));
            System.out.println("RSVP to Event - User ID: " + userId + ", Event ID: " + eventId);

            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Database connection established");

                // Insert RSVP into the database
                String sql = "INSERT INTO rsvps (user_id, event_id) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, eventId);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("RSVP to Event completed successfully");
                    response.sendRedirect("dashboard.html");
                } else {
                    System.out.println("Failed to insert RSVP into the database");
                    response.sendRedirect("dashboard.html?error=1");
                }
            } catch (Exception e) {
                System.out.println("Exception during RSVP to Event: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("dashboard.html?error=1");
            }
        } else if ("cancel_rsvp".equals(action)) {
            int eventId = Integer.parseInt(request.getParameter("event_id"));
            System.out.println("Cancel RSVP for Event - User ID: " + userId + ", Event ID: " + eventId);

            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Database connection established");

                // Delete RSVP from the database
                String sql = "DELETE FROM rsvps WHERE user_id = ? AND event_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, eventId);

                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("RSVP cancelled successfully");
                    response.sendRedirect("dashboard.html");
                } else {
                    System.out.println("Failed to cancel RSVP");
                    response.sendRedirect("dashboard.html?error=1");
                }
            } catch (Exception e) {
                System.out.println("Exception during Cancel RSVP: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("dashboard.html?error=1");
            }
        } else {
            System.out.println("Invalid action: " + action);
            response.sendRedirect("dashboard.html?error=1");
        }
    }
}