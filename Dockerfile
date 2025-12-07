# --- Build Stage ---
FROM gradle:8.5-jdk21-alpine AS build

WORKDIR /app

COPY . .
RUN gradle clean build -x test --no-daemon

# --- Runtime Stage ---
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
