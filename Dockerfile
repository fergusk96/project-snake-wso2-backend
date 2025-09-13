FROM gradle:8.7-jdk17 AS builder

WORKDIR /app
COPY . .
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=builder /app/build/libs/*-all.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]