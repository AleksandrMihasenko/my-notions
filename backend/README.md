# Backend

> Spring Boot REST API for my-notions project

---

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup & Installation](#setup--installation)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [API Documentation](#api-documentation)
- [Database](#database)
- [Architecture](#architecture)

---

## 🎯 Overview

RESTful API backend for a Notion-like knowledge management app. Built with Spring Boot to learn:

- REST API design & best practices
- Spring ecosystem (DI, Data, Security, WebSocket)
- SQL fundamentals & database design
- Testing (unit, integration, test containers)
- Clean architecture & separation of concerns

**Learning Approach**: Start simple (JDBC), add complexity progressively (JPA → Events → WebSocket)

---

## Tech Stack

### Core
- **Java**: 17+
- **Spring Boot**: 3.x
- **Build Tool**: Maven
- **Database**: MySQL 14+

### Spring Modules
- **Spring Web**: REST controllers
- **Spring Data JDBC**: Raw SQL
- **Spring Data JPA**: ORM
- **Spring Security**: JWT authentication
- **Spring WebSocket**: Real-time features
- **Spring Validation**: Request validation
- **Spring Events**: Event-driven patterns

### Infrastructure
- **Flyway**: Database migrations
- **Lombok**: Reduce boilerplate
- **MapStruct**: DTO mapping (maybe)

### Testing
- **JUnit 5**: Unit tests
- **Mockito**: Mocking
- **TestContainers**: Integration tests with real MySQL
- **RestAssured**: API testing (optional)

### Documentation
- **SpringDoc OpenAPI**: Swagger UI

---

## 📁 Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/mynotions/
│   │   │   ├── MyNotionsApplication.java       # Main entry point
│   │   │   │
│   │   │   ├── controller/                     # REST endpoints
│   │   │   │   ├── AuthController.java         # /api/auth/*
│   │   │   │   ├── WorkspaceController.java    # /api/workspaces/*
│   │   │   │   └── PageController.java         # /api/pages/*
│   │   │   │
│   │   │   ├── service/                        # Business logic
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── WorkspaceService.java
│   │   │   │   └── PageService.java
│   │   │   │
│   │   │   ├── repository/                     # Data access
│   │   │   │   ├── UserRepository.java         # JDBC (Month 1-2)
│   │   │   │   ├── WorkspaceRepository.java    # or JPA (Month 3+)
│   │   │   │   └── PageRepository.java
│   │   │   │
│   │   │   ├── entity/                         # Database models
│   │   │   │   ├── User.java
│   │   │   │   ├── Workspace.java
│   │   │   │   └── Page.java
│   │   │   │
│   │   │   ├── dto/                            # Data Transfer Objects
│   │   │   │   ├── request/
│   │   │   │   │   ├── RegisterRequest.java
│   │   │   │   │   └── CreatePageRequest.java
│   │   │   │   └── response/
│   │   │   │       ├── AuthResponse.java
│   │   │   │       └── PageResponse.java
│   │   │   │
│   │   │   ├── config/                         # Configuration
│   │   │   │   ├── SecurityConfig.java         # JWT, CORS
│   │   │   │   ├── WebConfig.java              # Web settings
│   │   │   │   └── OpenApiConfig.java          # Swagger
│   │   │   │
│   │   │   ├── exception/                      # Error handling
│   │   │   │   ├── GlobalExceptionHandler.java # @ControllerAdvice
│   │   │   │   ├── NotFoundException.java
│   │   │   │   └── ValidationException.java
│   │   │   │
│   │   │   └── util/                           # Utilities
│   │   │       ├── JwtUtil.java                # JWT helper
│   │   │       └── PasswordUtil.java           # BCrypt helper
│   │   │
│   │   └── resources/
│   │       ├── application.yml                 # Main config
│   │       ├── application-dev.yml             # Dev profile
│   │       ├── application-prod.yml            # Prod profile
│   │       │
│   │       └── db/migration/                   # Flyway migrations
│   │           ├── V1__create_users_table.sql
│   │           ├── V2__create_workspaces_table.sql
│   │           └── V3__create_pages_table.sql
│   │
│   └── test/
│       └── java/com/mynotions/
│           ├── controller/                     # Integration tests
│           │   ├── AuthControllerTest.java     # @SpringBootTest
│           │   └── PageControllerTest.java     # @WebMvcTest
│           │
│           ├── service/                        # Unit tests
│           │   ├── AuthServiceTest.java        # with Mockito
│           │   └── PageServiceTest.java
│           │
│           └── repository/                     # Repository tests
│               └── PageRepositoryTest.java     # @DataJdbcTest or TestContainers
│
├── pom.xml                                     # Maven dependencies
└── README.md                                   # This file
```

### Evolution of Structure

**Current**:
- Simple layered: Controller → Service → Repository
- Raw SQL with JDBC
- Basic DTOs

**Future**:
- Add JPA entities
- Domain events
- More sophisticated DTOs
- Maybe separate modules (core, infra, api)

---

## Setup & Installation

### Prerequisites

- **Java 17+** (check: `java -version`)
- **Maven 3.8+** (check: `mvn -version`)
- **Docker** (for PostgreSQL)
- **MySQL 14+** (or use Docker)

### 1. Clone & Navigate

```bash
git clone https://github.com/AleksandrMihasenko/my-notions.git
cd my-notions/backend
```

### 2. Start MySQL

**Option A: Docker Compose** (Recommended)
```bash
# From project root
docker-compose up -d postgres
```

**Option B: Local MySQL**
```bash
# Create database
createdb mynotions_dev
```

### 3. Install Dependencies

```bash
mvn clean install
```

---

## Running the Application

### Development Mode

```bash
# Run with dev profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Or
./mvnw spring-boot:run
```

**Backend runs on**: `http://localhost:8080`

**Swagger UI**: `http://localhost:8080/swagger-ui.html`

### Production Mode

```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/my-notions-backend-0.0.1-SNAPSHOT.jar
```

### Docker

```bash
# Build image
docker build -t my-notions-backend .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/mynotions_dev \
  my-notions-backend
```

---

## Testing

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=AuthServiceTest
```

### Run with Coverage

```bash
mvn clean test jacoco:report

# View report at: target/site/jacoco/index.html
```

### Test Types

**Unit Tests** (`service/`, `util/`):
- Fast, isolated
- Mock dependencies with Mockito
- Test business logic

**Integration Tests** (`controller/`):
- Use `@SpringBootTest` or `@WebMvcTest`
- Test HTTP endpoints
- Mock services or use real services

**Repository Tests** (`repository/`):
- Use TestContainers for real PostgreSQL
- Test SQL queries and data access

### Example Unit Test

```java
@ExtendWith(MockitoExtension.class)
class PageServiceTest {
    @Mock
    private PageRepository pageRepository;
    
    @InjectMocks
    private PageService pageService;
    
    @Test
    void createPage_Success() {
        // Arrange
        CreatePageRequest request = new CreatePageRequest("My Page", "Content");
        Page expectedPage = new Page(1L, "My Page", "Content");
        when(pageRepository.save(any())).thenReturn(expectedPage);
        
        // Act
        Page result = pageService.createPage(request);
        
        // Assert
        assertThat(result.getTitle()).isEqualTo("My Page");
        verify(pageRepository).save(any());
    }
}
```

### Example Integration Test

```java
@SpringBootTest
@AutoConfigureMockMvc
class PageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void createPage_ReturnsCreated() throws Exception {
        mockMvc.perform(post("/api/pages")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test\",\"content\":\"Content\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test"));
    }
}
```
---

## API Documentation

### Swagger UI

Once running, visit: **http://localhost:8080/swagger-ui.html**

### Key Endpoints (Planned)

#### Authentication
```
POST   /api/auth/register    # Register new user
POST   /api/auth/login       # Login (get JWT)
POST   /api/auth/refresh     # Refresh token
```

#### Workspaces
```
GET    /api/workspaces       # List user's workspaces
POST   /api/workspaces       # Create workspace
GET    /api/workspaces/{id}  # Get workspace details
PUT    /api/workspaces/{id}  # Update workspace
DELETE /api/workspaces/{id}  # Delete workspace
```

#### Pages
```
GET    /api/workspaces/{workspaceId}/pages  # List pages in workspace
POST   /api/workspaces/{workspaceId}/pages  # Create page
GET    /api/pages/{id}                      # Get page
PUT    /api/pages/{id}                      # Update page
DELETE /api/pages/{id}                      # Delete page
```

### Authentication

Most endpoints require JWT token:

```bash
# 1. Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password"}'

# Response: {"token": "eyJhbGc..."}

# 2. Use token in requests
curl http://localhost:8080/api/workspaces \
  -H "Authorization: Bearer eyJhbGc..."
```

---

## Database

### Migrations (Flyway)

Migrations in: `src/main/resources/db/migration/`

**Naming**: `V{version}__{description}.sql`

Examples:
- `V1__create_users_table.sql`
- `V2__create_workspaces_table.sql`
- `V3__add_email_verification.sql`

**Run migrations**:
```bash
mvn flyway:migrate
```

**View migration status**:
```bash
mvn flyway:info
```
---

## Architecture

### Current: Simple Layered Architecture

```
┌─────────────────────────────────────┐
│         Controller Layer            │  ← REST endpoints, request validation
│   (HTTP, JSON, @RestController)    │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│          Service Layer              │  ← Business logic, transactions
│      (@Service, @Transactional)    │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│        Repository Layer             │  ← Data access, SQL
│   (@Repository, JDBC/JPA)          │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│          Database                   │  ← PostgreSQL
│        (PostgreSQL)                 │
└─────────────────────────────────────┘
```

### Principles

**Separation of Concerns**:
- Controllers: Handle HTTP, validate input, return responses
- Services: Business logic, orchestration, transactions
- Repositories: Data access only, no business logic
- Entities: Database models
- DTOs: API contracts, separate from entities

**Dependencies**:
- Controller → Service → Repository
- Never: Repository → Service
- Never: skip layers (Controller → Repository directly)

### Evolution Plan

**Future**: Add complexity as needed
- DTO layer for all endpoints
- Domain events (Spring Events)
- Maybe: Clean Architecture (core/application/infrastructure modules)
- Maybe: CQRS elements (for real-time features)

---

## Development Guidelines

### Code Style

- **Follow Spring conventions**: `@Service`, `@Repository`, `@RestController`
- **Constructor injection**: Prefer over field injection
- **Immutable DTOs**: Use records where possible (Java 17+)
- **Validation**: Use `@Valid` and Bean Validation annotations

### Testing Guidelines

- **Test naming**: `methodName_condition_expectedResult`
- **AAA pattern**: Arrange, Act, Assert
- **One assertion per test** (when possible)
- **Mock external dependencies**
- **Use TestContainers** for repository tests

---
