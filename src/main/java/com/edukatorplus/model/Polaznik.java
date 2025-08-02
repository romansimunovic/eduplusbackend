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
    private String spol;
    private String telefon;
    private String grad;
    private String status;

    public Polaznik() {}

    public Polaznik(String ime, String prezime, String email, Integer godinaRodenja, String spol, String telefon, String grad, String status) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.godinaRodenja = godinaRodenja;
        this.spol = spol;
        this.telefon = telefon;
        this.grad = grad;
        this.status = status;
    }

    // Getteri i setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getGodinaRodenja() { return godinaRodenja; }
    public void setGodinaRodenja(Integer godinaRodenja) { this.godinaRodenja = godinaRodenja; }

    public String getSpol() { return spol; }
    public void setSpol(String spol) { this.spol = spol; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getGrad() { return grad; }
    public void setGrad(String grad) { this.grad = grad; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 
