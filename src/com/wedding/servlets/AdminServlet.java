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

public class AdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AdminServlet - doPost method called");
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            System.out.println("Unauthorized access - Redirecting to login page");
            response.sendRedirect("login.html");
            return;
        }

        String action = request.getParameter("action");
        System.out.println("Action: " + action);

        if ("create_wedding".equals(action)) {
            // Handle wedding creation
            String name = request.getParameter("name");
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String venue = request.getParameter("venue");
            int createdBy = (int) session.getAttribute("user_id");

            System.out.println("Creating wedding...");
            System.out.println("Wedding details - Name: " + name + ", Date: " + date + ", Time: " + time + ", Venue: " + venue);
            System.out.println("Created by user ID: " + createdBy);

            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Database connection established");
                String sql = "INSERT INTO weddings (name, date, time, venue, created_by) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, date);
                pstmt.setString(3, time);
                pstmt.setString(4, venue);
                pstmt.setInt(5, createdBy);
                pstmt.executeUpdate();
                System.out.println("Wedding created successfully");
                response.sendRedirect("admin-dashboard.html");
            } catch (Exception e) {
                System.out.println("Exception during wedding creation: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("admin-dashboard.html?error=1");
            }
        } else if ("create_event".equals(action)) {
            // Handle event creation
            String name = request.getParameter("name");
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String venue = request.getParameter("venue");
            int weddingId = Integer.parseInt(request.getParameter("wedding_id"));

            System.out.println("Creating event...");
            System.out.println("Event details - Name: " + name + ", Date: " + date + ", Time: " + time + ", Venue: " + venue + ", Wedding ID: " + weddingId);

            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Database connection established");
                String sql = "INSERT INTO events (name, date, time, venue, wedding_id) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, date);
                pstmt.setString(3, time);
                pstmt.setString(4, venue);
                pstmt.setInt(5, weddingId);
                pstmt.executeUpdate();
                System.out.println("Event created successfully");
                response.sendRedirect("admin-dashboard.html");
            } catch (Exception e) {
                System.out.println("Exception during event creation: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("admin-dashboard.html?error=1");
            }
        } else if ("edit_wedding".equals(action)) {
            // Handle wedding update
            int weddingId = Integer.parseInt(request.getParameter("wedding_id"));
            String name = request.getParameter("name");
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String venue = request.getParameter("venue");

            System.out.println("Updating wedding...");
            System.out.println("Wedding details - ID: " + weddingId + ", Name: " + name + ", Date: " + date + ", Time: " + time + ", Venue: " + venue);

            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Database connection established");
                String sql = "UPDATE weddings SET name = ?, date = ?, time = ?, venue = ? WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, date);
                pstmt.setString(3, time);
                pstmt.setString(4, venue);
                pstmt.setInt(5, weddingId);
                pstmt.executeUpdate();
                System.out.println("Wedding updated successfully");
                response.sendRedirect("admin-dashboard.html");
            } catch (Exception e) {
                System.out.println("Exception during wedding update: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("admin-dashboard.html?error=1");
            }
        } else if ("edit_event".equals(action)) {
            // Handle event update
            int eventId = Integer.parseInt(request.getParameter("event_id"));
            String name = request.getParameter("name");
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String venue = request.getParameter("venue");

            System.out.println("Updating event...");
            System.out.println("Event details - ID: " + eventId + ", Name: " + name + ", Date: " + date + ", Time: " + time + ", Venue: " + venue);

            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Database connection established");
                String sql = "UPDATE events SET name = ?, date = ?, time = ?, venue = ? WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, date);
                pstmt.setString(3, time);
                pstmt.setString(4, venue);
                pstmt.setInt(5, eventId);
                pstmt.executeUpdate();
                System.out.println("Event updated successfully");
                response.sendRedirect("admin-dashboard.html");
            } catch (Exception e) {
                System.out.println("Exception during event update: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("admin-dashboard.html?error=1");
            }
        } else if ("delete_wedding".equals(action)) {
            // Handle wedding deletion
            int weddingId = Integer.parseInt(request.getParameter("wedding_id"));

            System.out.println("Deleting wedding...");
            System.out.println("Wedding ID: " + weddingId);

            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Database connection established");
                String sql = "DELETE FROM weddings WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, weddingId);
                pstmt.executeUpdate();
                System.out.println("Wedding deleted successfully");
                response.sendRedirect("admin-dashboard.html");
            } catch (Exception e) {
                System.out.println("Exception during wedding deletion: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("admin-dashboard.html?error=1");
            }
        } else if ("delete_event".equals(action)) {
            // Handle event deletion
            int eventId = Integer.parseInt(request.getParameter("event_id"));
        
            System.out.println("Deleting event...");
            System.out.println("Event ID: " + eventId);
        
            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Database connection established");
        
                // Check if the event exists
                String checkSql = "SELECT * FROM events WHERE id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setInt(1, eventId);
                ResultSet rs = checkStmt.executeQuery();
        
                if (rs.next()) {
                    System.out.println("Event found: " + rs.getString("name"));
        
                    // Delete related RSVPs first
                    String deleteRsvpsSql = "DELETE FROM rsvps WHERE event_id = ?";
                    PreparedStatement deleteRsvpsStmt = conn.prepareStatement(deleteRsvpsSql);
                    deleteRsvpsStmt.setInt(1, eventId);
                    int rsvpsDeleted = deleteRsvpsStmt.executeUpdate();
                    System.out.println("Deleted " + rsvpsDeleted + " RSVPs related to the event.");
        
                    // Delete the event
                    String deleteEventSql = "DELETE FROM events WHERE id = ?";
                    PreparedStatement deleteEventStmt = conn.prepareStatement(deleteEventSql);
                    deleteEventStmt.setInt(1, eventId);
                    int rowsDeleted = deleteEventStmt.executeUpdate();
        
                    if (rowsDeleted > 0) {
                        System.out.println("Event deleted successfully");
                        response.sendRedirect("admin-dashboard.html");
                    } else {
                        System.out.println("No rows deleted. Event ID might not exist.");
                        response.sendRedirect("admin-dashboard.html?error=1");
                    }
                } else {
                    System.out.println("Event not found in the database.");
                    response.sendRedirect("admin-dashboard.html?error=1");
                }
            } catch (Exception e) {
                System.out.println("Exception during event deletion: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("admin-dashboard.html?error=1");
            }
        } else if ("delete_rsvp".equals(action)) {
            // Handle RSVP deletion
            int rsvpId = Integer.parseInt(request.getParameter("rsvp_id"));
        
            System.out.println("Deleting RSVP...");
            System.out.println("RSVP ID: " + rsvpId);
        
            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Database connection established");
        
                // Check if the RSVP exists
                String checkSql = "SELECT * FROM rsvps WHERE id = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setInt(1, rsvpId);
                ResultSet rs = checkStmt.executeQuery();
        
                if (rs.next()) {
                    System.out.println("RSVP found: " + rs.getInt("id"));
        
                    // Delete the RSVP
                    String deleteSql = "DELETE FROM rsvps WHERE id = ?";
                    PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                    deleteStmt.setInt(1, rsvpId);
                    int rowsDeleted = deleteStmt.executeUpdate();
        
                    if (rowsDeleted > 0) {
                        System.out.println("RSVP deleted successfully");
                        response.sendRedirect("rsvp-details.html");
                    } else {
                        System.out.println("No rows deleted. RSVP ID might not exist.");
                        response.sendRedirect("rsvp-details.html?error=1");
                    }
                } else {
                    System.out.println("RSVP not found in the database.");
                    response.sendRedirect("rsvp-details.html?error=1");
                }
            } catch (Exception e) {
                System.out.println("Exception during RSVP deletion: " + e.getMessage());
                e.printStackTrace();
                response.sendRedirect("rsvp-details.html?error=1");
            }
        } else {
            System.out.println("Invalid action: " + action);
            response.sendRedirect("admin-dashboard.html?error=1");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AdminServlet - doGet method called");
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            System.out.println("Unauthorized access - Redirecting to login page");
            response.sendRedirect("login.html");
            return;
        }

        String action = request.getParameter("action");
        System.out.println("Action: " + action);

        if ("fetch_weddings_and_events".equals(action)) {
            // Fetch and return the list of weddings and events as JSON
            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Fetching weddings and events...");

                // Fetch weddings
                String weddingSql = "SELECT * FROM weddings";
                PreparedStatement weddingStmt = conn.prepareStatement(weddingSql);
                ResultSet weddingRs = weddingStmt.executeQuery();

                List<Wedding> weddings = new ArrayList<>();
                while (weddingRs.next()) {
                    Wedding wedding = new Wedding();
                    wedding.setId(weddingRs.getInt("id"));
                    wedding.setName(weddingRs.getString("name"));
                    wedding.setDate(weddingRs.getString("date"));
                    wedding.setTime(weddingRs.getString("time"));
                    wedding.setVenue(weddingRs.getString("venue"));

                    // Fetch events for this wedding
                    String eventSql = "SELECT * FROM events WHERE wedding_id = ?";
                    PreparedStatement eventStmt = conn.prepareStatement(eventSql);
                    eventStmt.setInt(1, wedding.getId());
                    ResultSet eventRs = eventStmt.executeQuery();

                    List<Event> events = new ArrayList<>();
                    while (eventRs.next()) {
                        Event event = new Event();
                        event.setId(eventRs.getInt("id"));
                        event.setName(eventRs.getString("name"));
                        event.setDate(eventRs.getString("date"));
                        event.setTime(eventRs.getString("time"));
                        event.setVenue(eventRs.getString("venue"));
                        events.add(event);
                    }

                    wedding.setEvents(events);
                    weddings.add(wedding);
                }

                // Convert the list of weddings and events to JSON
                StringBuilder json = new StringBuilder("{\"weddings\":[");
                for (int i = 0; i < weddings.size(); i++) {
                    Wedding wedding = weddings.get(i);
                    if (i > 0) {
                        json.append(",");
                    }
                    json.append("{\"id\":").append(wedding.getId())
                        .append(",\"name\":\"").append(wedding.getName()).append("\"")
                        .append(",\"date\":\"").append(wedding.getDate()).append("\"")
                        .append(",\"time\":\"").append(wedding.getTime()).append("\"")
                        .append(",\"venue\":\"").append(wedding.getVenue()).append("\"")
                        .append(",\"events\":[");
                    for (int j = 0; j < wedding.getEvents().size(); j++) {
                        Event event = wedding.getEvents().get(j);
                        if (j > 0) {
                            json.append(",");
                        }
                        json.append("{\"id\":").append(event.getId())
                            .append(",\"name\":\"").append(event.getName()).append("\"")
                            .append(",\"date\":\"").append(event.getDate()).append("\"")
                            .append(",\"time\":\"").append(event.getTime()).append("\"")
                            .append(",\"venue\":\"").append(event.getVenue()).append("\"}");
                    }
                    json.append("]}");
                }
                json.append("]}");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json.toString());
            } catch (Exception e) {
                System.out.println("Exception fetching weddings and events: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching weddings and events");
            }
        } else if ("fetch_weddings".equals(action)) {
            // Fetch and return the list of weddings for the dropdown
            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Fetching weddings for dropdown...");

                // Fetch weddings
                String weddingSql = "SELECT * FROM weddings";
                PreparedStatement weddingStmt = conn.prepareStatement(weddingSql);
                ResultSet weddingRs = weddingStmt.executeQuery();

                List<Wedding> weddings = new ArrayList<>();
                while (weddingRs.next()) {
                    Wedding wedding = new Wedding();
                    wedding.setId(weddingRs.getInt("id"));
                    wedding.setName(weddingRs.getString("name"));
                    weddings.add(wedding);
                }

                // Convert the list of weddings to JSON
                StringBuilder json = new StringBuilder("{\"weddings\":[");
                for (int i = 0; i < weddings.size(); i++) {
                    Wedding wedding = weddings.get(i);
                    if (i > 0) {
                        json.append(",");
                    }
                    json.append("{\"id\":").append(wedding.getId())
                        .append(",\"name\":\"").append(wedding.getName()).append("\"}");
                }
                json.append("]}");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json.toString());
            } catch (Exception e) {
                System.out.println("Exception fetching weddings for dropdown: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching weddings for dropdown");
            }
        } 
        else if ("fetch_event".equals(action)) {
            // Fetch event data for edit page
            int eventId = Integer.parseInt(request.getParameter("event_id"));
        
            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Fetching event data for edit...");
        
                String sql = "SELECT * FROM events WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, eventId);
                ResultSet rs = pstmt.executeQuery();
        
                if (rs.next()) {
                    Event event = new Event();
                    event.setId(rs.getInt("id"));
                    event.setName(rs.getString("name"));
                    event.setDate(rs.getString("date"));
                    event.setTime(rs.getString("time"));
                    event.setVenue(rs.getString("venue"));
        
                    // Convert event object to JSON
                    String json = "{\"id\":" + event.getId() + 
                                  ",\"name\":\"" + event.getName() + "\"" +
                                  ",\"date\":\"" + event.getDate() + "\"" +
                                  ",\"time\":\"" + event.getTime() + "\"" +
                                  ",\"venue\":\"" + event.getVenue() + "\"}";
        
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Event not found");
                }
            } catch (Exception e) {
                System.out.println("Exception fetching event for edit: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching event data");
            }
        }else if ("fetch_rsvp_details".equals(action)) {
            // Fetch and return the list of RSVPs with user and event details as JSON
            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Fetching RSVP details...");

                // Join rsvps, users, and events tables to fetch username and event name
                String sql = "SELECT r.id AS rsvp_id, u.full_name AS username, e.name AS event_name " +
                             "FROM rsvps r " +
                             "JOIN users u ON r.user_id = u.id " +
                             "JOIN events e ON r.event_id = e.id";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                List<RSVPDetail> rsvpDetails = new ArrayList<>();
                while (rs.next()) {
                    RSVPDetail rsvpDetail = new RSVPDetail();
                    rsvpDetail.setRsvpId(rs.getInt("rsvp_id"));
                    rsvpDetail.setUsername(rs.getString("username"));
                    rsvpDetail.setEventName(rs.getString("event_name"));
                    rsvpDetails.add(rsvpDetail);
                }

                // Convert the list of RSVP details to JSON
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < rsvpDetails.size(); i++) {
                    RSVPDetail rsvpDetail = rsvpDetails.get(i);
                    if (i > 0) {
                        json.append(",");
                    }
                    json.append("{\"rsvp_id\":").append(rsvpDetail.getRsvpId())
                        .append(",\"username\":\"").append(rsvpDetail.getUsername()).append("\"")
                        .append(",\"event_name\":\"").append(rsvpDetail.getEventName()).append("\"}");
                }
                json.append("]");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json.toString());
            } catch (Exception e) {
                System.out.println("Exception fetching RSVP details: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching RSVP details");
            }
        } else if ("fetch_wedding_for_edit".equals(action)) {
            // Fetch wedding data for edit page
            int weddingId = Integer.parseInt(request.getParameter("wedding_id"));
        
            try (Connection conn = DatabaseConnection.getConnection()) {
                System.out.println("Fetching wedding data for edit...");
        
                String sql = "SELECT * FROM weddings WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, weddingId);
                ResultSet rs = pstmt.executeQuery();
        
                if (rs.next()) {
                    Wedding wedding = new Wedding();
                    wedding.setId(rs.getInt("id"));
                    wedding.setName(rs.getString("name"));
                    wedding.setDate(rs.getString("date"));
                    wedding.setTime(rs.getString("time"));
                    wedding.setVenue(rs.getString("venue"));
        
                    // Convert wedding object to JSON
                    String json = "{\"id\":" + wedding.getId() + 
                                  ",\"name\":\"" + wedding.getName() + "\"" +
                                  ",\"date\":\"" + wedding.getDate() + "\"" +
                                  ",\"time\":\"" + wedding.getTime() + "\"" +
                                  ",\"venue\":\"" + wedding.getVenue() + "\"}";
        
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Wedding not found");
                }
            } catch (Exception e) {
                System.out.println("Exception fetching wedding for edit: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching wedding data");
            }
        } else {
            System.out.println("Invalid action: " + action);
            response.sendRedirect("admin-dashboard.html?error=1");
        }
    }
}