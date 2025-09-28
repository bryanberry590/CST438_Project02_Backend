# CST438_Project02_Backend

Spring Boot backend API for our React Native Expo mobile application. This backend connects to a JawsDB MySQL database hosted on Heroku and runs in a Docker container.

## Prerequisites

- **Docker Desktop** installed and running
- **Java 17** (if running locally without Docker)
- **Git** for cloning the repository

## Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd CST438_Project02_Backend
```

### 2. Running the Application

You have two options to run the backend:

#### Option A: Docker Container (Recommended)
This runs the app in the same containerized environment as production.

1. **Build the Docker image:**
   ```bash
   docker build -t cst438-project02-backend .
   ```

2. **Run the container:**
   ```bash
   docker run -p 8080:8080 cst438-project02-backend
   ```

#### Option B: Local Development (Gradle)
This runs the app directly on your machine for faster development.

1. **Run with Gradle:**
   ```bash
   ./gradlew bootRun
   ```

### 3. Test the Application

Once the app is running, test that it's working:

**Using curl:**
```bash
curl http://localhost:8080/api/test
```

**Or visit in browser:**
```
http://localhost:8080/api/test
```

**Expected output:** `Backend is running!`

### 4. Stop the Application

- **Docker:** Press `Ctrl + C` in terminal, or stop container in Docker Desktop
- **Gradle:** Press `Ctrl + C` in terminal

## Database Configuration

The application is pre-configured to connect to our shared JawsDB MySQL database on Heroku. No additional database setup is required.

## Development Workflow

1. **For quick changes:** Use `./gradlew bootRun` for faster startup
2. **For testing containerized setup:** Use Docker commands
3. **Before pushing code:** Test with Docker to ensure it works in production environment

## API Endpoints

- `GET /api/test` - Health check endpoint
- Api Endpoints will be in '/src/main/java/com/project02/springboot/controller'

## Troubleshooting

- **Port 8080 already in use:** Stop any other apps running on port 8080
- **Docker build fails:** Ensure Docker Desktop is running
- **Database connection issues:** Check that the application.properties has correct JawsDB credentials

## Project Structure

```
src/
├── main/
│   ├── java/com/project02/springboot/
│   │   ├── SpringbootApplication.java
│   │   └── controller/
│   │       └── TestEndpoints.java
│   └── resources/
│       └── application.properties
└── test/
```