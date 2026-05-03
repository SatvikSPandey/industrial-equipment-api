# Industrial Equipment & Work Order Management API

A production-grade REST API for managing industrial equipment, maintenance work orders, and maintenance logs. Built with Spring Boot 3.5, Spring Security JWT, PostgreSQL, Docker, and Azure DevOps CI/CD.

## Live Demo

- **API Base URL:** https://industrial-equipment-api.onrender.com
- **Swagger UI:** https://industrial-equipment-api.onrender.com/swagger-ui/index.html

> Note: Hosted on Render free tier — first request may take 50 seconds to wake up.

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.5.14 |
| Language | Java 17 |
| Security | Spring Security + JWT (JJWT 0.12.6) |
| Database | PostgreSQL (Supabase) |
| ORM | Spring Data JPA + Hibernate |
| Build | Maven 3.9 |
| API Docs | SpringDoc OpenAPI / Swagger UI |
| Tests | JUnit 5 + Mockito |
| Container | Docker (multi-stage build) |
| CI/CD | Azure DevOps Pipelines |
| Deployment | Render (Docker image from Docker Hub) |

## API Endpoints

### Authentication (Public)
| Method | Endpoint | Description |
|---|---|---|
| POST | /api/auth/register | Register a new user |
| POST | /api/auth/login | Login and receive JWT token |

### Equipment (JWT Required)
| Method | Endpoint | Description |
|---|---|---|
| GET | /api/equipment | Get all equipment |
| POST | /api/equipment | Create new equipment |
| GET | /api/equipment/{id} | Get equipment by ID |
| PUT | /api/equipment/{id} | Update equipment |
| DELETE | /api/equipment/{id} | Delete equipment (ADMIN only) |

### Work Orders (JWT Required)
| Method | Endpoint | Description |
|---|---|---|
| GET | /api/workorders | Get all work orders |
| POST | /api/workorders | Create new work order |
| GET | /api/workorders/{id} | Get work order by ID |
| PUT | /api/workorders/{id} | Update work order |
| DELETE | /api/workorders/{id} | Delete work order (ADMIN only) |
| GET | /api/workorders/equipment/{id} | Get work orders by equipment |

### Maintenance Logs (JWT Required)
| Method | Endpoint | Description |
|---|---|---|
| GET | /api/maintenance-logs | Get all maintenance logs |
| POST | /api/maintenance-logs | Create maintenance log |
| GET | /api/maintenance-logs/workorder/{id} | Get logs by work order |

## Role-Based Access Control

| Role | Permissions |
|---|---|
| ADMIN | Full access including DELETE |
| ENGINEER | Read + Create + Update equipment and work orders |
| TECHNICIAN | Read + Update work orders + Create maintenance logs |

## Running Locally

```bash
# Clone the repository
git clone https://github.com/SatvikSPandey/industrial-equipment-api.git
cd industrial-equipment-api

# Set environment variables (see imp/secrets.txt for reference)
# DB_URL, DB_USERNAME, DB_PASSWORD, APP_JWT_SECRET, APP_JWT_EXPIRY_MS

# Run with local profile
mvn spring-boot:run "-Dspring-boot.run.profiles=local"
```

## Running with Docker

```bash
docker pull ******/industrial-equipment-api:latest
docker run -p 8080:8080 \
  -e DB_URL=your_db_url \
  -e DB_USERNAME=your_username \
  -e DB_PASSWORD=your_password \
  -e APP_JWT_SECRET=your_secret \
  -e APP_JWT_EXPIRY_MS=86400000 \
  ******/industrial-equipment-api:latest
```

## CI/CD Pipeline

Azure DevOps pipeline automatically triggers on every push to `main`:
1. **Build and Test** — Maven build with Java 17
2. **Docker Build and Push** — Builds multi-stage Docker image and pushes to Docker Hub

## Project Structure

src/
├── main/java/com/satvik/equipment/
│   ├── config/          # Security, JWT, OpenAPI configuration
│   ├── controller/      # REST controllers
│   ├── dto/             # Request and response DTOs
│   ├── entity/          # JPA entities
│   ├── enums/           # Role, Status, Priority enums
│   ├── exception/       # Global exception handler
│   ├── repository/      # Spring Data JPA repositories
│   └── service/         # Business logic
└── test/                # JUnit 5 unit tests

## Author

**Satvik Pandey**
- GitHub: [github.com/SatvikSPandey](https://github.com/SatvikSPandey)
- LinkedIn: [linkedin.com/in/satvikpandey-433555365](https://linkedin.com/in/satvikpandey-433555365)
- Portfolio: [satvikspandey.netlify.app](https://satvikspandey.netlify.app)
