package com.edukatorplus.model;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password; // hashirano!
    private String ime;
    private String prezime;
    private String role = "USER"; // "USER" ili "ADMIN"

    // getteri i setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email;}
    public void setEmail(String email) { this.email = email;}
    public String getPassword() { return password;}
    public void setPassword(String password) { this.password = password;}
    public String getIme() { return ime;}
    public void setIme(String ime) { this.ime = ime;}
    public String getPrezime() { return prezime;}
    public void setPrezime(String prezime) { this.prezime = prezime;}
    public String getRole() { return role;}
    public void setRole(String role) { this.role = role;}
}
