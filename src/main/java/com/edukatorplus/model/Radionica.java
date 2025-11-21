package com.edukatorplus.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "radionice")
public class Radionica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;
    private LocalDate datum;
    private String opis;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
    public LocalDate getDatum() { return datum; }
    public void setDatum(LocalDate datum) { this.datum = datum; }
    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }
}
