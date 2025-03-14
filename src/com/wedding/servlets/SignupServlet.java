package com.wedding.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import com.wedding.utils.DatabaseConnection;

public class SignupServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SignupServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("SignupServlet: Request received."); // Log info message

        String fullName = request.getParameter("full_name");
        String email = request.getParameter("email");
        String password = BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt());
        String securityQuestion = request.getParameter("security_question");
        String securityAnswer = request.getParameter("security_answer");

        logger.info("Full Name: " + fullName); // Log info message
        logger.info("Email: " + email); // Log info message
        logger.info("Security Question: " + securityQuestion); // Log info message
        logger.info("Security Answer: " + securityAnswer); // Log info message

        try (Connection conn = DatabaseConnection.getConnection()) {
            logger.info("Database connection established."); // Log info message

            String sql = "INSERT INTO users (full_name, email, password, security_question, security_answer) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, securityQuestion);
            pstmt.setString(5, securityAnswer);

            logger.info("Executing SQL: " + sql); // Log info message

            int rowsInserted = pstmt.executeUpdate();
            logger.info(rowsInserted + " row(s) inserted."); // Log info message

            if (rowsInserted > 0) {
                logger.info("User registered successfully. Redirecting to login page."); // Log info message
                response.sendRedirect("login.html");
            } else {
                logger.warning("Failed to register user."); // Log warning message
                response.sendRedirect("signup.html?error=1");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in SignupServlet:", e); // Log error message
            response.sendRedirect("signup.html?error=1");
        }
    }
}