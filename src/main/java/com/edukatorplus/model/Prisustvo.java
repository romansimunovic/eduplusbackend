package com.edukatorplus.model;

import jakarta.persistence.*;

@Entity
public class Prisustvo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusPrisustva status;

    @ManyToOne
    @JoinColumn(name = "polaznik_id")
    private Polaznik polaznik;

    @ManyToOne
    @JoinColumn(name = "radionica_id")
    private Radionica radionica;

    // Getteri i setteri

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPrisustva getStatus() {
        return status;
    }

    public void setStatus(StatusPrisustva status) {
        this.status = status;
    }

    public Polaznik getPolaznik() {
        return polaznik;
    }

    public void setPolaznik(Polaznik polaznik) {
        this.polaznik = polaznik;
    }

    public Radionica getRadionica() {
        return radionica;
    }

    public void setRadionica(Radionica radionica) {
        this.radionica = radionica;
    }
}
