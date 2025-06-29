# 1. Koristi Maven sliku za build (Java 16)
FROM maven:3.8.4-openjdk-16 AS build

# 2. Postavi radni direktorij unutar kontejnera
WORKDIR /app

# 3. Kopiraj cijeli projekt u Docker image
COPY . /app

# 4. Pokreni Maven build kako bi se stvorila JAR datoteka
RUN mvn clean package -DskipTests

# 5. Drugi stage za stvaranje finalnog image-a
FROM openjdk:16-jdk-alpine

# 6. Postavi radni direktorij unutar kontejnera
WORKDIR /app

# 7. Kopiraj generiranu JAR datoteku iz prethodnog sloja u finalni image
COPY --from=build /app/target/edukatorplus-backend-1.0-SNAPSHOT.jar /app/eduplus-backend.jar

# 8. Pokreni aplikaciju
ENTRYPOINT ["java", "-jar", "/app/eduplus-backend.jar"]
