# DEV (lokalno): OK je brisati i puniti fake podatke
spring.datasource.url=jdbc:postgresql://localhost:5432/eduplus_dev
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

spring.jackson.date-format=yyyy-MM-dd
spring.jackson.serialization.WRITE_DATES_AS_TIMESTAMPS=false
