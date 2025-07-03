# EdukatorPlus Backend

**EdukatorPlus** je RESTful backend sustav razvijen u okviru kolegija **Informacijsko-komunikacijska infrastruktura** na Odsjeku za informacijske znanosti u sklopu diplomskog studija Informacijske tehnologije na  Filozofskom fakultetu u Osijeku. Projekt je mentorirao **izv. prof. dr. sc. Tomislav Jakopec**.

Backend aplikacija implementirana je u Java Spring Boot frameworku te koristi Maven za upravljanje projektom. U aplikaciji su primijenjena znanja stečena i kroz kolegij **Programiranje 2 (P2)** s naglaskom na objektno orijentirano programiranje u Javi.

## \:sparkles: Funkcionalnosti

* CRUD operacije za entitete: `Polaznik`, `Radionica`, `Prisustvo`
* Validacija i obrada grešaka
* Enum za status prisustva (`PRISUTAN`, `IZOSTAO`, `ODUSTAO`)
* DTO pattern za odvajanje slojeva aplikacije
* Statistički prikaz prisustva po radionici
* Odvojeni slojevi: `service`, `controller`, `repository`, `model`, `dto`
* REST API (Spring Web)

## \:gear: Tehnologije

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* H2 / PostgreSQL (ovisno o konfiguraciji)
* Maven

## \:hammer\_and\_wrench: Pokretanje projekta

```bash
mvn spring-boot:run
```

Backend se prema zadanim postavkama pokreće na:

```
http://localhost:8080
```

## \:earth\_africa: Deploy

Backend Render URL:

```
https://eduplusbackend.onrender.com
```

## \:file\_folder: Struktura projekta

```
src/main/java/com/edukatorplus/
├── controller/
├── service/
├── repository/
├── model/
└── dto/
```

## \:link: API Primjeri ruta

* `GET /api/polaznici`
* `POST /api/radionice`
* `GET /api/prisustva/view`
* `DELETE /api/prisustva/{id}`

## \:mortar\_board: Akademski kontekst

Ova aplikacija je izrađena kao završni projekt u sklopu kolegija **Informacijsko-komunikacijska infrastruktura** uz podršku umjetne inteligencije (ChatGPT Plus) pod mentorstvom **izv. prof. dr. sc. Tomislava Jakopeca**.

---
