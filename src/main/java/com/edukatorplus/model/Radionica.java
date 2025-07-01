package com.edukatorplus.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Radionica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    private LocalDate datum;

    public Radionica() {}

    public Radionica(String naziv, LocalDate datum) {
        this.naziv = naziv;
        this.datum = datum;
    }

    public Long getId() { return id; }
    public String getNaziv() { return naziv; }
    public LocalDate getDatum() { return datum; }

    public void setId(Long id) { this.id = id; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
    public void setDatum(LocalDate datum) { this.datum = datum; }
}
