FROM openjdk:17-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y dos2unix && rm -rf /var/lib/apt/lists/*

COPY gradlew .
COPY gradle gradle/
COPY build.gradle .
COPY settings.gradle .

RUN dos2unix ./gradlew && chmod +x ./gradlew

COPY src src/

# Use bootJar instead of build for Spring Boot apps
RUN ./gradlew clean bootJar -x test --stacktrace

# Copy the executable JAR
RUN cp build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]