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
import java.sql.Timestamp;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.wedding.utils.DatabaseConnection;

public class ContactServlet extends HttpServlet {
    
    // Handle form submission
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ContactServlet - doPost method called");
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        
        System.out.println("Received contact form submission:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Database connection established");
            
            String sql = "INSERT INTO contact_submissions (name, email, subject, message, submission_date) " +
                         "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, subject);
            pstmt.setString(4, message);
            
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Contact form submission saved successfully");
                response.sendRedirect("contact.html?success=1");
            } else {
                System.out.println("Failed to save contact form submission");
                response.sendRedirect("contact.html?error=1");
            }
        } catch (Exception e) {
            System.out.println("Exception during contact form submission: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("contact.html?error=1");
        }
    }
    
    // Handle fetching contact submissions for admin
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ContactServlet - doGet method called");
        
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            System.out.println("Unauthorized access - Redirecting to login page");
            response.sendRedirect("login.html");
            return;
        }
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Fetching contact submissions...");
            
            String sql = "SELECT * FROM contact_submissions ORDER BY submission_date DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            List<ContactSubmission> submissions = new ArrayList<>();
            while (rs.next()) {
                ContactSubmission submission = new ContactSubmission();
                submission.setId(rs.getInt("id"));
                submission.setName(rs.getString("name"));
                submission.setEmail(rs.getString("email"));
                submission.setSubject(rs.getString("subject"));
                submission.setMessage(rs.getString("message"));
                submission.setSubmissionDate(rs.getTimestamp("submission_date"));
                submissions.add(submission);
            }
            
            // Convert to JSON
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < submissions.size(); i++) {
                ContactSubmission submission = submissions.get(i);
                if (i > 0) {
                    json.append(",");
                }
                json.append("{\"id\":").append(submission.getId())
                    .append(",\"name\":\"").append(escapeJson(submission.getName())).append("\"")
                    .append(",\"email\":\"").append(escapeJson(submission.getEmail())).append("\"")
                    .append(",\"subject\":\"").append(escapeJson(submission.getSubject())).append("\"")
                    .append(",\"message\":\"").append(escapeJson(submission.getMessage())).append("\"")
                    .append(",\"submission_date\":\"").append(submission.getSubmissionDate()).append("\"}");
            }
            json.append("]");
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            
        } catch (Exception e) {
            System.out.println("Exception fetching contact submissions: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching contact submissions");
        }
    }
    
    private String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
    
    // Inner class for contact submissions
    private static class ContactSubmission {
        private int id;
        private String name;
        private String email;
        private String subject;
        private String message;
        private Timestamp submissionDate;
        
        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Timestamp getSubmissionDate() { return submissionDate; }
        public void setSubmissionDate(Timestamp submissionDate) { this.submissionDate = submissionDate; }
    }
}