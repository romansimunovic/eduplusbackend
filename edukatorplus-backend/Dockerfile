# 1. Koristi službenu sliku za Maven (Java 16)
FROM maven:3.8.4-openjdk-16-slim AS build

# 2. Postavi radni direktorij unutar kontejnera
WORKDIR /app

# 3. Kopiraj pom.xml i preuzmi ovisnosti
COPY pom.xml .

# 4. Preuzmi ovisnosti (samo ako su promijenjeni pom.xml)
RUN mvn dependency:go-offline

# 5. Kopiraj cijeli kod u kontejner
COPY src ./src

# 6. Buildaj aplikaciju i stvori JAR datoteku
RUN mvn clean package -DskipTests

# 7. Drugi stage: koristi OpenJDK sliku za pokretanje aplikacije
FROM openjdk:16-jdk-alpine

# 8. Postavi radni direktorij
WORKDIR /app

# 9. Kopiraj JAR datoteku iz build stage-a
COPY --from=build /app/target/eduplus-backend-1.0-SNAPSHOT.jar /app/eduplus-backend.jar

# 10. Pokreni JAR datoteku
CMD ["java", "-jar", "eduplus-backend.jar"]
