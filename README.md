# 📚 Online Book Store Management System

A desktop-based Online Book Store application developed using **Java Swing**, **MySQL**, **JDBC**, and **Object-Oriented Programming (OOP)** principles. The system provides a complete bookstore management solution for both customers and administrators.

---

## 🚀 Features

### 👤 User Features

* User Registration & Login
* Browse Available Books
* Search Books by Title, Author, or Category
* Add Books to Cart
* Wishlist Management
* Order Management
* User Profile Management
* Order History Tracking

### 🔐 Admin Features

* Secure Admin Login
* User Management
* Inventory Management
* Category Management
* Order Monitoring
* Report Generation

### ⚙️ Technical Features

* Java Swing GUI
* MySQL Database Integration
* JDBC Connectivity
* DAO Architecture
* Exception Handling
* Multithreading Support
* Invoice Generation
* File Handling Utilities

---

## 🛠️ Technology Stack

| Category             | Technology                  |
| -------------------- | --------------------------- |
| Programming Language | Java                        |
| GUI Framework        | Java Swing                  |
| Database             | MySQL                       |
| Connectivity         | JDBC                        |
| Version Control      | Git & GitHub                |
| Design Approach      | Object-Oriented Programming |

---

## 📂 Project Structure

```text
onlinebookstore/
│
├── src/
│   └── com/bookstore/
│       ├── dao/
│       ├── db/
│       ├── exception/
│       ├── model/
│       ├── service/
│       ├── ui/
│       └── util/
│
├── database/
├── docs/
├── invoices/
├── lib/
└── README.md
```

---

## 🎯 OOP Concepts Implemented

* Encapsulation
* Inheritance
* Polymorphism
* Abstraction
* Interfaces
* Exception Handling

---

## 🗄️ Database Features

* User Authentication
* Book Management
* Inventory Tracking
* Order Processing
* Wishlist Management
* Category Management

---

## ▶️ How to Run

### Clone Repository

```bash
git clone https://github.com/samruddhimhaske/onlinebookstore.git
```

### Configure Database

1. Install MySQL.
2. Create database.
3. Execute:

```sql
source database/schema.sql;
source database/data.sql;
```

4. Update credentials in:

```java
DBConnection.java
```

### Run Application

Compile and execute:

```bash
javac -cp ".:lib/*" src/com/bookstore/main/MainApplication.java
java -cp ".:lib/*" com.bookstore.main.MainApplication
```

---

## 📈 Learning Outcomes

* Java Desktop Application Development
* Database Design & Integration
* JDBC Connectivity
* CRUD Operations
* GUI Development
* Software Architecture
* Git & GitHub Version Control

---

## 👩‍💻 Developer

**Samruddhi Mhaske**

Computer Engineering Student

GitHub: https://github.com/samruddhimhaske

---

## 📜 License

This project is developed for educational and academic purposes.
