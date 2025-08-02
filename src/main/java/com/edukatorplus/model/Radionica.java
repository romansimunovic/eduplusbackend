package com.edukatorplus.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDate;

@Entity
public class Radionica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    @Lob
    @Column
    private String opis;
    
    private LocalDate datum;

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
