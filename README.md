﻿# Wedding Management System

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
![Screenshot 2025-03-14 165527](https://github.com/user-attachments/assets/6af5b3f1-191e-4bd9-8433-c02898ed6fc1)
![Screenshot 2025-03-14 165520](https://github.com/user-attachments/assets/def9b624-e526-4bec-b969-ee617f2cbe6c)
![Screenshot 2025-03-14 165512](https://github.com/user-attachments/assets/3cbaebf5-6df5-4e1b-b317-2dd3c9984b4b)
![Screenshot 2025-03-14 164949](https://github.com/user-attachments/assets/40ab39de-2f13-4e9a-ab6f-114bdfc4fabe)
![Screenshot 2025-03-14 164938](https://github.com/user-attachments/assets/fdd5f9aa-3f9b-4a25-8f35-9f16776d8d05)
![Screenshot 2025-03-14 164928](https://github.com/user-attachments/assets/76d3dce8-dcdb-4415-9a06-925196b8e71c)
![Screenshot 2025-03-14 164850](https://github.com/user-attachments/assets/46a41af9-0d0c-42d3-8f77-a63c6e037a05)
![Screenshot 2025-03-14 164303](https://github.com/user-attachments/assets/43476c1c-3fd4-422a-9736-ca0ab5fddd5d)
![Screenshot 2025-03-14 164252](https://github.com/user-attachments/assets/600f41f9-7fb2-4b67-ab7e-fedb0306c9aa)
![Screenshot 2025-03-14 164240](https://github.com/user-attachments/assets/74fa18f3-3d35-4ec9-bb03-70ae64321688)
![Screenshot 2025-03-14 164223](https://github.com/user-attachments/assets/8c0d7876-9449-4fd2-a91b-50a0501bc458)
![Screenshot 2025-03-14 163716](https://github.com/user-attachments/assets/1982e919-d6c3-41ab-b346-eb082bc4771e)
![Screenshot 2025-03-14 163654](https://github.com/user-attachments/assets/303621db-8ed8-4ee9-9b2a-9380fc494563)
![Screenshot 2025-03-14 163035](https://github.com/user-attachments/assets/f656a96e-1b28-44ae-86fb-d1ca57dad920)
![Screenshot 2025-03-14 163005](https://github.com/user-attachments/assets/a8b9928c-4076-407d-8605-545563fdab67)
![Screenshot 2025-03-14 160201](https://github.com/user-attachments/assets/9cf78a88-5d12-4960-9ed7-29e0ad657454)
![Screenshot 2025-03-14 160147](https://github.com/user-attachments/assets/be0aa4b6-184e-454a-bff5-c27055047a2f)
![Screenshot 2025-03-14 160135](https://github.com/user-attachments/assets/055d14d7-595a-495d-a028-5447f7c71184)
![Screenshot 2025-03-14 160105](https://github.com/user-attachments/assets/ec5992a3-4255-474f-aaae-05f0f8194424)
![Screenshot 2025-03-14 160050](https://github.com/user-attachments/assets/cbbc7aca-526d-48fd-a09f-fd64ca6ab435)


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
