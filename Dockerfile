# Build stage
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk
WORKDIR /app

# (opcija) brži start: dodaj JVM flags — prilagodi po želji
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080

# Render postavlja $PORT -> Springu ga guramo kao server.port
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=$PORT -jar app.jar"]
