package com.wedding.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import com.wedding.utils.DatabaseConnection;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Login attempt - Email: " + email + ", Password: " + password);

        // Hardcoded admin credentials
        if ("admin@wedding.com".equals(email) && "Admin@1234".equals(password)) {
            System.out.println("Admin login successful");
            HttpSession session = request.getSession();
            session.setAttribute("user_id", 2); // Use a special ID for admin
            session.setAttribute("role", "admin");
            response.sendRedirect("home.html"); // Redirect to home page
            return;
        }

        // Regular user login
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Connecting to database...");
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("User found in database: " + rs.getString("email"));
                String hashedPassword = rs.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    System.out.println("User login successful");
                    HttpSession session = request.getSession();
                    session.setAttribute("user_id", rs.getInt("id"));
                    session.setAttribute("role", "user");
                    response.sendRedirect("home.html"); // Redirect to home page
                } else {
                    System.out.println("Incorrect password");
                    response.sendRedirect("login.html?error=1");
                }
            } else {
                System.out.println("User not found in database");
                response.sendRedirect("login.html?error=1");
            }
        } catch (Exception e) {
            System.out.println("Exception during login: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("login.html?error=1");
        }
    }
}