# 1. Koristi Maven sliku za build (Java 17)
FROM maven:3.8.4-openjdk-17-slim AS build

# 2. Postavi radni direktorij unutar kontejnera
WORKDIR /usr/src/app

# 3. Kopiraj cijeli projekt u Docker image
COPY . /usr/src/app

# 4. Pokreni Maven build kako bi se stvorila JAR datoteka
RUN mvn clean package -DskipTests

# 5. Drugi stage za stvaranje finalnog image-a
FROM openjdk:17-jdk-alpine

# 6. Postavi radni direktorij unutar kontejnera
WORKDIR /usr/src/app

# 7. Kopiraj generiranu JAR datoteku iz prethodnog sloja u finalni image
COPY --from=build /usr/src/app/target/edukatorplus-backend-1.0-SNAPSHOT.jar /usr/src/app/eduplus-backend.jar

# 8. Pokreni aplikaciju
ENTRYPOINT ["java", "-jar", "/usr/src/app/eduplus-backend.jar"]
