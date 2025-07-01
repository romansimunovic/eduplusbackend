package com.edukatorplus.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Radionica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    @Column(nullable = false)
    private LocalDate datum;

    // Prazan konstruktor
    public Radionica() {
    }

    // Konstruktor s parametrima
    public Radionica(String naziv, LocalDate datum) {
        this.naziv = naziv;
        this.datum = datum;
    }

    // Getteri i setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
}
