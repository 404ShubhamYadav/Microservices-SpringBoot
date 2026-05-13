---

## Services

### 1. Eureka Server
- Service registry and discovery
- All services register here on startup
- Port: `8761`

### 2. API Gateway
- Single entry point for all requests
- Routes requests to correct service
- Port: `8080`

### 3. Student Service
- Manages student data
- Communicates with Book Service via FeignClient
- Port: `8081`

### 4. Book Service
- Manages book data
- Port: `8082`

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
| POST | `/addStudent` | Add new student |
| GET | `/getAllStudents` | Get all students |
| GET | `/getStudentById/{id}` | Get student by ID |
| GET | `/studentWithBook/{id}` | Get student with book details |
| GET | `/book/{id}` | Get book via FeignClient |

### Book Service
| Method | Endpoint | Description |
|---|---|---|
| POST | `/add` | Add new book |
| GET | `/getBook/{id}` | Get book by ID |

---

## How FeignClient Works
Student Service                    Book Service
↓                                 ↓
@FeignClient(name="BOOK-SERVICE")       ↓
bookClient.getBook(id) ─────────────── /getBook/{id}
↓                                 ↓
Returns Book object ←───────────── Book data

## How to Run

### Prerequisites
Java 17+
MySQL
Maven

### Steps

```bash
# 1. Start Eureka Server first
cd EurekaServer
mvn spring-boot:run

# 2. Start Book Service
cd BookService
mvn spring-boot:run

# 3. Start Student Service
cd StudentService
mvn spring-boot:run

# 4. Start API Gateway
cd ApiGateway
mvn spring-boot:run
```
## Key Concepts Demonstrated
✅ Service Registration    - Eureka Server
✅ Service Discovery       - Services find each other
✅ Centralized Routing     - API Gateway
✅ Inter-service Communication - FeignClient
✅ Load Balancing          - Spring Cloud LoadBalancer

Microservices/
├── EurekaServer/
│   └── src/main/java/
├── ApiGateway/
│   └── src/main/java/
├── StudentService/
│   └── src/main/java/
│       ├── StudentController.java
│       ├── Student.java
│       ├── StudentRepository.java
│       ├── BookClient.java (FeignClient)
│       └── StudentBookResponse.java
├── BookService/
│   └── src/main/java/
│       ├── BookController.java
│       ├── Book.java
│       └── BookRepository.java
└── README.md