package com.wedding.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.wedding.utils.DatabaseConnection;

public class ForgotPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String step = request.getParameter("step");

        try (Connection conn = DatabaseConnection.getConnection()) {
            StringBuilder jsonResponse = new StringBuilder();

            if ("1".equals(step)) {
                // Step 1: Fetch security question
                String sql = "SELECT security_question FROM users WHERE email = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    jsonResponse.append("{\"success\":true,\"security_question\":\"")
                               .append(rs.getString("security_question")).append("\"}");
                } else {
                    jsonResponse.append("{\"success\":false}");
                }
            } else if ("2".equals(step)) {
                // Step 2: Verify security answer
                String securityAnswer = request.getParameter("security_answer");
                String sql = "SELECT * FROM users WHERE email = ? AND security_answer = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                pstmt.setString(2, securityAnswer);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    jsonResponse.append("{\"success\":true}");
                } else {
                    jsonResponse.append("{\"success\":false}");
                }
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}