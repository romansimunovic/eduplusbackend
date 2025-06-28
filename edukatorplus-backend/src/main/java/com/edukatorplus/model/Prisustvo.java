package com.edukatorplus.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Prisustvo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long polaznikId;
    private Long radionicaId;

    @Enumerated(EnumType.STRING)
    private StatusPrisustva status;  // Koristimo Enum za status prisustva

    // Getteri i setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolaznikId() {
        return polaznikId;
    }

    public void setPolaznikId(Long polaznikId) {
        this.polaznikId = polaznikId;
    }

    public Long getRadionicaId() {
        return radionicaId;
    }

    public void setRadionicaId(Long radionicaId) {
        this.radionicaId = radionicaId;
    }

    public StatusPrisustva getStatus() {
        return status;
    }

    public void setStatus(StatusPrisustva status) {
        this.status = status;
    }
}
