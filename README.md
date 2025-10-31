**EdukatorPlus – Backend**

Ovo je repozitorij za backend dio aplikacije EdukatorPlus, izrađen u Spring Bootu. Njegova uloga je pružiti REST API za radionice, polaznike i njihovo prisustvo, tako da frontend uvijek radi s aktualnim podacima.

Backend omogućuje dodavanje, uređivanje i brisanje zapisa, praćenje statusa prisustva te automatsko generiranje demo podataka. Povezan je s PostgreSQL bazom i hostan na Renderu, gdje komunicira direktno s frontend aplikacijom.

Projekt je razvijen u Javi (Spring Boot + JPA/Hibernate), uz Maven i PostgreSQL. 

Pokreće se jednostavno – nakon kloniranja repozitorija dovoljno je ./mvnw spring-boot:run, a API je dostupan na http://localhost:8080.

_Ovo je dio završnog rada iz kolegija Informacijsko-komunikacijska infrastruktura pod mentorstvom izv. prof. dr. sc. Tomislava Jakopeca. U dokumentaciji i kodu korišten je i ChatGPT Plus kao podrška._
