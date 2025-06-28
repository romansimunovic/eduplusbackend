# Koristi službenu sliku Jave (openjdk)
FROM openjdk:16-jdk-alpine

# Postavi radni direktorij unutar kontejnera
WORKDIR /app

# Kopiraj JAR datoteku koja je buildana iz target foldera u Docker image
COPY target/eduplus-backend-1.0-SNAPSHOT.jar /app/eduplus-backend.jar

# Pokreni aplikaciju
CMD ["java", "-jar", "eduplus-backend.jar"]

