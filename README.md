# EduPlus Backend

Ovo je repozitorij za backend dio aplikacije **EduPlus**, izrađen u sklopu kolegija  
**Informacijsko-komunikacijska infrastruktura** pod mentorstvom **izv. prof. dr. sc. Tomislava Jakopeca**.

Projekt prikazuje osnovni model sustava za evidenciju polaznika, radionica i prisustava.  
Aplikacija je pojednostavljena i trenutno koristi **mockirane (statičke) podatke** bez aktivne baze.  
U dokumentaciji i kodu korišten je i **ChatGPT Plus** kao podrška u razvoju i optimizaciji.

---

## Opis

EduPlus je zamišljen kao sustav za jednostavno praćenje i administraciju edukativnih radionica.  
Backend je implementiran u **Spring Bootu** te omogućuje osnovne API pozive za:
- popis radionica  
- popis polaznika  
- evidenciju prisustava  
- testnu provjeru dostupnosti (ping endpoint)

---

Tehnologije: Java 17, Spring Boot, Maven

## Pokretanje

```bash mvn spring-boot:run - a backend se pokreće na adresi http://localhost:8080.
