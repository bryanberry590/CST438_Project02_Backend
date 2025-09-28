# Use official OpenJDK 17 runtime as base image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy source code
COPY src src

# Make gradlew executable
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew build -x test

# Copy the built jar file (find the executable jar, exclude plain jar)
RUN find build/libs -name "*.jar" -not -name "*-plain.jar" -exec cp {} app.jar \;

# Expose port 8080
EXPOSE 8080

# Set the startup command
ENTRYPOINT ["java", "-jar", "/app/app.jar"]