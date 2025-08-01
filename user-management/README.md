# User Management System - Spring Boot CRUD Application

## 🎓 Learning Objectives for College Students

This project demonstrates a complete **Spring Boot CRUD (Create, Read, Update, Delete)** application for user management. It's designed to teach you:

- **Spring Boot Architecture** - Understanding the layered architecture
- **RESTful APIs** - Building HTTP endpoints
- **MongoDB Integration** - NoSQL database operations
- **Data Transfer Objects (DTOs)** - Data transformation between layers
- **Validation** - Input validation and error handling
- **Service Layer Pattern** - Business logic separation
- **Repository Pattern** - Data access abstraction

## 📁 Project Structure

```
src/main/java/com/revature/user_management/
├── UserManagementApplication.java          # Main Spring Boot application
├── config/
│   └── MongoConfig.java                   # MongoDB configuration
├── controller/
│   └── UserController.java                # REST API endpoints
├── dto/
│   ├── UserRequestDto.java               # Input data validation
│   └── UserResponseDto.java              # Output data formatting
├── exception/
│   └── GlobalExceptionHandler.java        # Global exception handling
├── model/
│   └── User.java                         # Database entity
├── repository/
│   └── UserRepository.java               # Data access layer
├── service/
│   └── UserService.java                  # Business logic interface
└── service/impl/
    └── UserServiceImpl.java              # Business logic implementation
```

## 🏗️ Architecture Layers Explained

### 1. **Controller Layer** (`controller/`)
- **Purpose**: Handles HTTP requests and responses
- **Responsibilities**: 
  - Receives HTTP requests
  - Validates input data
  - Calls service layer
  - Returns HTTP responses
- **Key Concepts**: `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`

### 2. **Service Layer** (`service/` and `service/impl/`)
- **Purpose**: Contains business logic
- **Responsibilities**:
  - Business rules and validation
  - Data transformation
  - Orchestrates repository operations
- **Key Concepts**: `@Service`, dependency injection, interface-based design

### 3. **Repository Layer** (`repository/`)
- **Purpose**: Data access abstraction
- **Responsibilities**:
  - Database operations
  - Query methods
  - CRUD operations
- **Key Concepts**: `@Repository`, `MongoRepository`, custom queries

### 4. **Model Layer** (`model/`)
- **Purpose**: Represents database entities
- **Responsibilities**:
  - Database mapping
  - Entity relationships
  - Data structure definition
- **Key Concepts**: `@Document`, `@Id`, `@Indexed`

### 5. **DTO Layer** (`dto/`)
- **Purpose**: Data transformation between layers
- **Responsibilities**:
  - Input validation (`UserRequestDto`)
  - Output formatting (`UserResponseDto`)
  - API contract definition
- **Key Concepts**: `@Valid`, validation annotations, Lombok annotations

### 6. **Exception Layer** (`exception/`)
- **Purpose**: Centralized exception handling
- **Responsibilities**:
  - Global exception handling
  - Custom error responses
  - Validation error formatting
- **Key Concepts**: `@RestControllerAdvice`, `@ExceptionHandler`

## 🚀 Getting Started

### Prerequisites
- Java 21
- MongoDB (running on localhost:27017)
- Gradle

### Running the Application

1. **Start MongoDB**:
   ```bash
   # Install MongoDB if not already installed
   # Start MongoDB service
   mongod
   ```

2. **Run the Application**:
   ```bash
   ./gradlew bootRun
   ```

3. **Access the API**:
   - Health Check: `http://localhost:8080/api/users/health`
   - Swagger UI (if added): `http://localhost:8080/swagger-ui.html`

## 📡 API Endpoints

### User Management Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/users` | Create a new user |
| `GET` | `/api/users` | Get all users |
| `GET` | `/api/users/{id}` | Get user by ID |
| `GET` | `/api/users/email/{email}` | Get user by email |
| `PUT` | `/api/users/{id}` | Update user |
| `DELETE` | `/api/users/{id}` | Delete user |

### Search and Filter Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/users/active` | Get active users only |
| `GET` | `/api/users/role/{role}` | Get users by role |
| `GET` | `/api/users/search/firstname/{firstName}` | Search by first name |
| `GET` | `/api/users/search/lastname/{lastName}` | Search by last name |

### Utility Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/users/health` | Health check |
| `GET` | `/api/users/exists/email/{email}` | Check if email exists |
| `GET` | `/api/users/count/role/{role}` | Count users by role |
| `PATCH` | `/api/users/{id}/status` | Activate/deactivate user |

## 📝 Example API Usage

### Create a User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "123-456-7890",
    "address": "123 Main St, City, State",
    "role": "USER"
  }'
```

### Get All Users
```bash
curl -X GET http://localhost:8080/api/users
```

### Update a User
```bash
curl -X PUT http://localhost:8080/api/users/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "role": "ADMIN"
  }'
```

## 🧪 Testing the Application

### Using Postman or curl

1. **Create a user**:
   ```bash
   curl -X POST http://localhost:8080/api/users \
     -H "Content-Type: application/json" \
     -d '{"email":"test@example.com","firstName":"Test","lastName":"User"}'
   ```

2. **Get all users**:
   ```bash
   curl -X GET http://localhost:8080/api/users
   ```

3. **Get user by ID** (replace `{id}` with actual user ID):
   ```bash
   curl -X GET http://localhost:8080/api/users/{id}
   ```

## 🔧 Key Spring Boot Concepts Demonstrated

### 1. **Dependency Injection**
```java
@Autowired
public UserController(UserService userService) {
    this.userService = userService;
}
```

### 2. **RESTful API Design**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(...) { }
}
```

### 3. **Data Validation**
```java
@NotBlank(message = "Email is required")
@Email(message = "Email should be valid")
private String email;
```

### 4. **MongoDB Integration**
```java
@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String email;
}
```

### 5. **Lombok Integration**
```java
@Data                    // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor       // Generates default constructor
@AllArgsConstructor     // Generates constructor with all fields
@RequiredArgsConstructor // Generates constructor for final fields
public class UserDto {
    private String email;
    private String firstName;
    private String lastName;
}
```

### 6. **Service Layer Pattern**
```java
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    // Business logic implementation
}
```

## 📚 Learning Path for Students

1. **Start with the Model**: Understand how `User.java` represents database entities
2. **Explore DTOs**: Learn how `UserRequestDto` and `UserResponseDto` handle data transformation
3. **Study Repository**: See how `UserRepository` provides data access methods
4. **Understand Service**: Learn business logic in `UserServiceImpl`
5. **Master Controller**: See how `UserController` exposes REST endpoints
6. **Test the API**: Use the provided curl commands to test functionality

## 🎯 Key Takeaways

- **Separation of Concerns**: Each layer has a specific responsibility
- **Dependency Injection**: Spring manages object creation and dependencies
- **Data Validation**: Input validation prevents invalid data
- **RESTful Design**: Consistent API design patterns
- **Database Abstraction**: Repository pattern hides database complexity
- **Error Handling**: Proper HTTP status codes and error responses

## 🔍 Next Steps for Learning

1. Add unit tests using JUnit and Mockito
2. Implement authentication and authorization
3. Add logging and monitoring
4. Create a frontend application to consume the API
5. Add database migrations
6. Implement caching with Redis
7. Add API documentation with Swagger
8. Explore more Lombok annotations (@Builder, @Slf4j, etc.)
9. Implement custom exceptions in the exception package

---

**Happy Learning! 🚀**

This project provides a solid foundation for understanding Spring Boot development and RESTful API design. Each component is thoroughly documented to help you understand the concepts and patterns used in modern web development. 