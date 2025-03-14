# Wedding Management System

## Overview
The Wedding Management System is a web-based application designed using Java Servlets, JSP, Apache Tomcat, MySQL, HTML, CSS, and JavaScript. It provides two primary roles:
- **Admin:** Can create and manage weddings/events.
- **User (Guest):** Can RSVP to weddings and events.

This system ensures secure authentication, session management, and SQL injection protection.

## Features
### User (Guest) Functionalities
- **Home Page:** Overview of the website.
- **Registration:**
  - Full Name
  - Unique Email
  - Secure Password (BCrypt hashed)
  - Security Question & Answer for password reset
- **Login & Logout:**
  - Username: Email & Password
  - Proper session management
- **Dashboard:**
  - View all RSVP'd weddings and events
  - Event details: Date, Time, Venue
  - Cancel RSVP if needed
- **RSVP:**
  - Direct RSVP to weddings
  - RSVP to specific events under a wedding
- **Forgot Password:**
  - Answer Security Question
  - Reset password securely
- **Additional Pages:**
  - Contact Us Page
  - Gallery Page

### Admin Functionalities
- **Admin Credentials:** (Hardcoded for now)
  - Email: `admin@wedding.com`
  - Password: `Admin@1234`
- **Admin Dashboard:**
  - Create, Edit, Delete Weddings & Events
  - View users who RSVP'd
  - View RSVP details
- **Logout:** Secure session termination

## Security Features
- **Password Reset Security:** Security question-based reset instead of OTP.
- **Session Management:**
  - Sessions invalidated correctly on logout.
  - Restricted pages based on roles.
- **SQL Injection Protection:**
  - Use of Prepared Statements.
- **Duplicate RSVP Prevention:**
  - Users can RSVP only once per event.
  - Option to cancel RSVP.
- **Secure Authentication:**
  - Passwords stored using BCrypt hashing.
- **Role-Based Access Control (RBAC):**
  - Restricted admin access to certain pages.
  - Only the event creator can modify it.
- **Google reCAPTCHA:**
  - Prevents bot signups.

## Project Flow
### User Journey (Guest)
1. Visit Home Page
2. Register/Login
3. Access Dashboard
4. View & RSVP to events
5. Cancel RSVP if needed
6. Logout

### Admin Journey
1. Login (Admin Credentials)
2. Create/Edit/Delete Weddings & Events
3. View RSVP details
4. Logout

## Tech Stack
- **Frontend:** HTML, CSS, JavaScript
- **Backend:** Java Servlets, JSP, Apache Tomcat
- **Database:** MySQL
- **Security:** BCrypt hashing, Prepared Statements

## Project Directory Structure
```
WeddingManagement/
├── index.html                 <-- Homepage
├── login.html                 <-- Login Page
├── signup.html                <-- Signup Page
├── forgot-password.html       <-- Forgot Password Page
├── dashboard.html             <-- User Dashboard
├── admin-dashboard.html       <-- Admin Dashboard
├── contact-us.html            <-- Contact Page
├── gallery.html               <-- Gallery Page
├── events.html                <-- Events Page
├── rsvp.html                  <-- RSVP Page
├── assets/
│   ├── css/
│   │   ├── styles.css         <-- CSS Styling
│   ├── js/
│   │   ├── script.js          <-- JavaScript for UI Interactions
│   ├── images/
│   │   ├── logo.png           <-- Website Logo
│   │   ├── banner.jpg         <-- Homepage Banner
├── WEB-INF/
│   ├── web.xml                <-- Web Configuration (Servlet Mappings)
│   ├── lib/                   <-- JAR Files (Dependencies)
│   │   ├── servlet-api.jar    <-- Required for Servlets
│   │   ├── mysql-connector-java-8.0.33.jar  <-- MySQL JDBC Driver
│   ├── classes/               <-- Compiled Java Classes
│   │   ├── com/wedding/servlets/
│   │   │   ├── LoginServlet.class
│   │   │   ├── SignupServlet.class
│   │   │   ├── ForgotPasswordServlet.class
│   │   │   ├── ResetPasswordServlet.class
│   │   │   ├── DashboardServlet.class
│   │   │   ├── AdminServlet.class
│   │   │   ├── RSVPServlet.class
│   │   │   ├── EventServlet.class
├── src/                         <-- Source Code (Java)
│   ├── com/wedding/servlets/
│   │   ├── LoginServlet.java
│   │   ├── SignupServlet.java
│   │   ├── ForgotPasswordServlet.java
│   │   ├── ResetPasswordServlet.java
│   │   ├── DashboardServlet.java
│   │   ├── AdminServlet.java
│   │   ├── RSVPServlet.java
│   │   ├── EventServlet.java
├── database/
│   ├── wedding_management.sql  <-- Database Schema & Sample Data
├── README.md                    <-- Documentation
└── build.bat                     <-- Windows Script to Compile Java Files
```

## Screenshots
(Add screenshots of different pages here)

## Installation & Setup
1. Install **Apache Tomcat** and **MySQL**.
2. Configure `web.xml` with servlet mappings.
3. Import `wedding_management.sql` into MySQL.
4. Deploy the project to Tomcat.
5. Run the application in a browser.

## Contributing
Feel free to fork this repository and submit pull requests.

## License
This project is licensed under the MIT License.