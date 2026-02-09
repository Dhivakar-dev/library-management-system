# Library Management System - Backend API

A robust RESTful backend service built with **Spring Boot** and **MySQL** to manage comprehensive library operations. This project focuses on complex business logic, secure authentication, and a modular architecture.

## 🚀 Project Overview

This backend engine handles the core logic of a library system, moving beyond basic CRUD. It manages interconnected modules where book availability, user subscriptions, and payment statuses dynamically affect one another.

## 🛠 Tech Stack

* **Framework:** Spring Boot 3.x
* **Language:** Java 17+
* **Database:** MySQL
* **Security:** Spring Security & JWT (JSON Web Tokens)
* **ORM:** Spring Data JPA / Hibernate
* **Payment Gateway:** Razorpay SDK Integration
* **Tools:** Lombok, Maven, Postman



## ✨ Key Features

### 🔐 Security & User Management
* **JWT Authentication:** Secure login and stateless session management.
* **Role-Based Access Control (RBAC):** Distinct permissions for **Admin** (inventory & user management) and **Users** (borrowing & reservations).
* **Profile Management:** Endpoints to fetch authenticated user data and update profiles.

### 📚 Inventory & Hierarchical Genres
* **Smart Inventory:** Real-time tracking of total copies vs. available copies.
* **Recursive Genres:** Supports a hierarchical category structure (e.g., Fiction -> Mystery -> Detective) with unlimited depth.

### 💳 Subscriptions & Payments
* **Membership Plans:** Admin-defined plans that control borrowing limits and loan durations.
* **Razorpay Integration:** Automated payment link generation for subscriptions and fine settlements.
* **Event-Driven Logic:** Uses Spring Events to activate subscriptions immediately upon payment verification.

### 🔄 Borrowing Workflow
* **Loan Lifecycle:** Manages Checkout -> Renewal -> Return status transitions.
* **Automated Fines:** Penalties calculated automatically based on overdue days or book damage.
* **Reservation Queue:** A FIFO (First-In-First-Out) system for users waiting for currently unavailable books.

## 🏗 Architectural Highlights

* **Layered Architecture:** Strict separation between Controllers, Services, and Repositories.
* **DTO & Mapper Pattern:** Used to isolate the database entities from the API layer, ensuring clean data transfer.
* **Global Exception Handling:** Centralized `@ControllerAdvice` for consistent API error responses.
* **Soft Deletes:** Implemented to maintain data integrity while removing items from the active view.



## 📡 Core API Endpoints

| Module | Endpoint | Method | Description |
| :--- | :--- | :--- | :--- |
| **Auth** | `/api/auth/signup` | POST | Register a new user |
| **Books** | `/api/books/search` | GET | Advanced search with filters |
| **Loans** | `/api/book-loans/checkout` | POST | Borrow a book |
| **Payments**| `/api/payments/verify` | POST | Verify Razorpay transaction |
| **Admin** | `/api/admin/books` | POST | Add inventory (Admin Only) |

## ⚙️ Setup & Installation

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-username/library-backend.git](https://github.com/your-username/library-backend.git)
    ```

2.  **Configure MySQL:**
    Update `src/main/resources/application.properties` with your MySQL database credentials.

3.  **Set Razorpay Keys:**
    Add your `razorpay.key.id` and `razorpay.secret.key` to the properties file.

4.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

---
*Developed as a demonstration of backend architecture and business logic implementation.*
