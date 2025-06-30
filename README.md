# EdukatorPlus Backend

**EdukatorPlus** je RESTful backend sustav razvijen u okviru kolegija **Informacijsko-komunikacijska infrastruktura** na Odsjeku za informacijske znanosti Filozofskog fakulteta u Osijeku. Projekt je mentorirao **izv. prof. dr. sc. Tomislav Jakopec**.

Backend aplikacija implementirana je u Java Spring Boot frameworku te koristi Maven za upravljanje projektom. U aplikaciji su primijenjena znanja stečena i kroz kolegij **Programiranje 2 (P2)** s naglaskom na objektno orijentirano programiranje u Javi.

## ✨ Funkcionalnosti

- ✅ CRUD operacije za entitete: `Polaznik`, `Radionica`, `Prisustvo`
- ✅ Validacija i obrada grešaka
- ✅ Enum za status prisustva (`PRISUTAN`, `IZOSTAO`, `ODUSTAO`)
- ✅ DTO pattern za odvajanje slojeva aplikacije
- ✅ Statistički prikaz prisustva po radionici
- ✅ Odvojeni `service`, `controller`, `repository` i `model` slojevi
- ✅ Spring Boot + REST API + OpenAPI anotacije (Swagger kompatibilno)

## 🛠️ Tehnologije

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 / PostgreSQL
- Maven
- Swagger / OpenAPI

## 🔧 Pokretanje projekta

```bash
mvn spring-boot:run
