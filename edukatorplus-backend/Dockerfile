# Koristi službeni OpenJDK 16 image
FROM openjdk:16-jdk-alpine AS build

# Postavi radni direktorij unutar kontejnera
WORKDIR /app

# Kopiraj Maven build datoteku i pakete
COPY . /app

# Pokreni Maven build kako bi se stvorila JAR datoteka
RUN mvn clean package -DskipTests

# Kopiraj generiranu JAR datoteku u aplikaciju
COPY target/edukatorplus-backend-1.0-SNAPSHOT.jar /app/eduplus-backend.jar

# Pokreni aplikaciju
ENTRYPOINT ["java", "-jar", "/app/eduplus-backend.jar"]
