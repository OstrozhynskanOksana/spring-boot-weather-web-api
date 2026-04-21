# 🌦️ Spring Boot Weather API

RESTful backend application built with Spring Boot that provides real-time weather information and supports weather-based planning features.


## Overview

This project provides weather data for different cities via a clean REST API.

It demonstrates backend development practices such as:
- layered architecture
- external API integration
- DTO mapping
- configuration management
- clean REST API design

The application acts as a backend proxy between the client and an external weather provider (e.g. OpenWeatherMap). It retrieves weather data, processes it, and returns a simplified response.


## Extended Functionality

Beyond basic weather retrieval, the system includes:

- storing weather data by day
- linking weather conditions to user plans/events
- user-defined rules (e.g. temperature, rain)
- weather change detection
- notification logic based on user-defined conditions

This extends the application from a simple API into a weather-driven planning platform.


## Architecture

The application follows a layered architecture:

- **Controller Layer** — handles HTTP requests and global exception handler
- **Service Layer** — business logic 
- **Config Layer** — external weather API integration and security configuration
- **Repository Layer** — database access (JPA)  
- **DTO Layer** — internal data structures
- **Entity Layer** — database data
- **Exception Layer** — custom exceptions  
   

Additional components inside Service layer:
- weather update scheduling  
- notification logic (rules evaluation and alerts)  

### Flow

Client → Controller → Service → External API / Database → Service → Response


## Tech Stack

- Java 21  
- Spring Boot  
- Spring Web MVC  
- Spring Data JPA  
- Spring Security  
- JWT (jjwt)  
- PostgreSQL  
- Gradle  
- Docker / Docker Compose  


## Project Setup

### Prerequisites
* Docker installed.
* Java 21 (for local development).

### 1. Run with Docker (Recommended)

1. Clone this repository to your local machine:
```bash
git clone [github_repo_link]
```

2. Set up .env variables in the root folder:
```
# ───── Server ─────
PORT

# ───── Database ─────
DB_URL
DB_USERNAME
DB_PASSWORD

# ───── JWT ─────
JWT_SECRET
JWT_EXPIRATION
```

3. Run command from root folder:
```bash
docker-compose -f docker-compose.yml up --build
```

4. Once the command completes, the application will be accessible on http://localhost:8080


