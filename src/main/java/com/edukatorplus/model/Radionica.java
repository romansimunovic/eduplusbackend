package com.edukatorplus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Radionica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    private String opis; // ✅ NOVO

    private LocalDate datum;

    // 🔧 KONSTRUKTORI

    public Radionica() {
    }

    public Radionica(String naziv, String opis, LocalDate datum) {
        this.naziv = naziv;
        this.opis = opis;
        this.datum = datum;
    }

    public Radionica(String naziv, LocalDate datum) {
        this.naziv = naziv;
        this.datum = datum;
    }

    // ✅ GETTERI I SETTERI

    public Long getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
}
