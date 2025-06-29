# 1. Koristi Maven sliku za build (Java 17)
FROM maven:3.8.4-openjdk-17-slim AS build

# 2. Postavi radni direktorij unutar kontejnera
WORKDIR /usr/src/app

# 3. Kopiraj cijeli projekt u Docker image
COPY . /usr/src/app

# 4. Postavljanje WORKDIR na direktorij sa 'project files'
WORKDIR "/usr/src/app/project files"  # Ovdje stavljamo cijelu putanju u navodnike zbog razmaka

# 5. Pokreni Maven build kako bi se stvorila JAR datoteka
RUN mvn clean package -DskipTests

# 6. Drugi stage za stvaranje finalnog image-a
FROM openjdk:17-jdk-alpine

# 7. Postavi radni direktorij unutar kontejnera (korijen aplikacije)
WORKDIR /usr/src/app

# 8. Kopiraj generiranu JAR datoteku iz prethodnog sloja u finalni image
COPY --from=build /usr/src/app/project\ files/target/edukatorplus-backend-1.0-SNAPSHOT.jar /usr/src/app/eduplus-backend.jar

# 9. Pokreni aplikaciju
ENTRYPOINT ["java", "-jar", "/usr/src/app/eduplus-backend.jar"]
