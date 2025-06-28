# 1. Koristi službenu sliku OpenJDK 16 za pokretanje aplikacije
FROM openjdk:16-jdk-alpine AS base

# 2. Postavi radni direktorij unutar kontejnera
WORKDIR /app

# 3. Kopiraj JAR datoteku iz build direktorija u Docker image
COPY target/edukatorplus-backend-1.0-SNAPSHOT.jar /app/eduplus-backend.jar

# 4. Pokreni aplikaciju
CMD ["java", "-jar", "eduplus-backend.jar"]
