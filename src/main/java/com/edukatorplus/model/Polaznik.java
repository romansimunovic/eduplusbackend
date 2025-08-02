package com.edukatorplus.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Polaznik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ime;
    private String prezime;
    private String email;
    private Integer godinaRodenja;

    public Polaznik() {
        // obavezan prazan konstruktor za JPA
    }

    public Polaznik(String ime, String prezime, String email, Integer godinaRodenja) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.godinaRodenja = godinaRodenja;
    }

    // Getteri i setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGodinaRodenja() {
        return godinaRodenja;
    }

    public void setGodinaRodenja(Integer godinaRodenja) {
        this.godinaRodenja = godinaRodenja;
    }
}
