# Microservices Architecture — Spring Boot

Microservices architecture using Spring Boot, Eureka Server
for service discovery, API Gateway for centralized routing
and FeignClient for inter-service communication between
Student and Book service.

---

## Architecture Overview

    Client Request
          ↓
    API Gateway (port 8080)
          ↓
    Eureka Server (port 8761)
          ↓
    ___________↙___________
    ↓                      ↓
    Student Service        Book Service
    (port 8081)            (port 8082)
          ↓
    FeignClient calls BookService

---

## Services

### 1. Eureka Server
- Service registry and discovery
- All services register here on startup
- Port: 8761

### 2. API Gateway
- Single entry point for all requests
- Routes requests to correct service
- Port: 8080

### 3. Student Service
- Manages student data
- Communicates with Book Service via FeignClient
- Port: 8081

### 4. Book Service
- Manages book data
- Port: 8082

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Spring Boot | Backend framework |
| Spring Cloud Netflix Eureka | Service registry |
| Spring Cloud Gateway | API Gateway |
| OpenFeign | Inter-service communication |
| Spring Data JPA | Database operations |
| MySQL | Database |
| Lombok | Reduce boilerplate |
| Maven | Build tool |

---

## API Endpoints

### Student Service

| Method | Endpoint | Description |
|---|---|---|
| POST | /addStudent | Add new student |
| GET | /getAllStudents | Get all students |
| GET | /getStudentById/{id} | Get student by ID |
| GET | /studentWithBook/{id} | Get student with book details |
| GET | /book/{id} | Get book via FeignClient |

### Book Service

| Method | Endpoint | Description |
|---|---|---|
| POST | /add | Add new book |
| GET | /getBook/{id} | Get book by ID |

---

## How FeignClient Works

    Student Service calls bookClient.getBook(id)
          ↓
    FeignClient sends HTTP request to Book Service
          ↓
    Book Service returns Book data
          ↓
    Student Service receives Book object
    without writing any HTTP code manually

---

## How to Run

### Prerequisites
- Java 17+
- MySQL
- Maven

### Steps

1. Start Eureka Server first

        cd eurekaServer
        mvn spring-boot:run

2. Start Book Service

        cd BookService
        mvn spring-boot:run

3. Start Student Service

        cd student-service
        mvn spring-boot:run

4. Start API Gateway

        cd apiGateway
        mvn spring-boot:run

### Verify Services Registered

Open browser: http://localhost:8761

You should see all services registered

---

## Database Setup

Create databases in MySQL Workbench:

    CREATE DATABASE student_db;
    CREATE DATABASE book_db;

Update application.properties in each service:

    spring.datasource.url=jdbc:mysql://localhost:3306/your_db
    spring.datasource.username=root
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update

---

## Project Structure

    Microservices/
    ├── eurekaServer/
    ├── apiGateway/
    ├── student-service/
    │   ├── StudentController.java
    │   ├── Student.java
    │   ├── StudentRepository.java
    │   ├── BookClient.java
    │   └── StudentBookResponse.java
    ├── BookService/
    │   ├── BookController.java
    │   ├── Book.java
    │   └── BookRepository.java
    └── README.md

---

## Key Concepts Demonstrated

- Service Registration — Eureka Server
- Service Discovery — Services find each other
- Centralized Routing — API Gateway
- Inter-service Communication — FeignClient
- Load Balancing — Spring Cloud LoadBalancer

---

## Author

Shubham Kumar Yadav
GitHub: https://github.com/404ShubhamYadav