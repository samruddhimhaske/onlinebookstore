# Project Report: Online Book Store Application

## 1. Abstract
The "Online Book Store" is a robust Java-based desktop application designed to streamline the process of browsing, managing, and purchasing literary resources. It acts as an e-commerce platform simulator, demonstrating critical aspects of database interaction, business logic enforcement, and user interface design. By utilizing Core Java concepts natively—such as Exception Handling, Multithreading, and OOP paradigms—the project guarantees scalability and modularity. 

## 2. Introduction
E-commerce has rapidly morphed into an essential aspect of daily life. This project aims to bring the functionality of massive online retailers into a simplified, yet fully functional, desktop environment. The project caters to two primary entities: 
- **Users**: Who can browse books, manage their cart, and securely check out.
- **Administrators**: Who manage inventory, monitor orders, and handle reports.

## 3. Objectives
- To demonstrate practical usage of all Core Java Concepts (Inheritance, Abstraction, Polymorphism).
- To create a seamless Graphical User Interface using Java Swing natively without bloated third-party UI libraries.
- To maintain data coherency systematically through a normalized relational MySQL database.
- To implement dynamic invoice generation using Java File Handling capabilities.

## 4. Modules
### 4.1 Authentication Module
Manages login and registration for differing user roles. Employs `AuthService` and `UserDAO`.
### 4.2 Inventory Module
Allows Users to view active books and Administrators to add/update stock. Fully integrated with `BookDAO`.
### 4.3 Billing & Cart Module
Simulates an e-commerce cart. Users can dynamically add books; the system auto-calculates taxes and applicable discounts via the `BillingService`. Processes checkouts safely via database transactions.

## 5. Methodology & Tools
- **Frontend Layer**: Built exclusively using `javax.swing.*`, adopting a minimalist UI approach via a custom `BaseFrame`.
- **Backend Layer**: Java 8/11/17 (Core Java).
- **Database Layer**: MySQL querying via JDBC API (`java.sql.*`).
- **Architecture**: Separated into `model`, `dao`, `service`, `ui`, and `util` packages to mimic Enterprise MVC patterns.

## 6. Result & Conclusion
The application was successfully compiled and tested locally against the MySQL database. User flows—from logging into the system, adding products to the cart, viewing the splash screen, and receiving a locally saved invoice `.txt` file—behaved flawlessly. By leveraging pure Core Java, we avoided complex build tools, successfully yielding a lightweight, robust, and highly educational college-level application.

## 7. Future Scope
- Integration with external real-time Payment Gateways APIs (e.g., Stripe, Razorpay).
- Email notifications upon successful order checkout using JavaMail.
- Transitioning the Swing Interface into a Web-based interface (Java Spring Boot + React).
