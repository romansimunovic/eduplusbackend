EdukatorPlus BackendEdukatorPlus je RESTful backend sustav razvijen u okviru kolegija Informacijsko-komunikacijska infrastruktura na Odsjeku za informacijske znanosti Filozofskog fakulteta u Osijeku. Projekt je mentorirao izv. prof. dr. sc. Tomislav Jakopec.
Backend aplikacija implementirana je u Java Spring Boot frameworku te koristi Maven za upravljanje projektom. U aplikaciji su primijenjena znanja stečena i kroz kolegij Programiranje 2 (P2) s naglaskom na objektno orijentirano programiranje u Javi.
:sparkles: FunkcionalnostiCRUD operacije za entitete: Polaznik, Radionica, Prisustvo
Validacija i obrada grešaka
Enum za status prisustva (PRISUTAN, IZOSTAO, ODUSTAO)
DTO pattern za odvajanje slojeva aplikacije
Statistički prikaz prisustva po radionici
Odvojeni slojevi: service, controller, repository, model, dto
REST API (Spring Web)
:gear: TehnologijeJava 17
Spring Boot
Spring Web
Spring Data JPA
H2 / PostgreSQL (ovisno o konfiguraciji)
Maven
:hammer_and_wrench: Pokretanje projektamvn spring-boot:runBackend se prema zadanim postavkama pokreće na:
http://localhost:8080:earth_africa: DeployBackend Render URL:
https://eduplusbackend.onrender.com:file_folder: Struktura projektasrc/main/java/com/edukatorplus/
├── controller/
├── service/
├── repository/
├── model/
└── dto/:link: API Primjeri rutaGET /api/polaznici
POST /api/radionice
GET /api/prisustva/view
DELETE /api/prisustva/{id}
:mortar_board: Akademski kontekstOva aplikacija je izrađena kao završni projekt u sklopu kolegija Informacijsko-komunikacijska infrastruktura pod mentorstvom izv. prof. dr. sc. Tomislava Jakopeca.
