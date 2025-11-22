package com.edukatorplus.dto;


import com.edukatorplus.model.StatusPrisustva;

public class PrisustvoDTO {
    private Long id;
    private Long polaznikId;
    private Long radionicaId;
    private StatusPrisustva status;
    
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPolaznikId() { return polaznikId; }
    public void setPolaznikId(Long polaznikId) { this.polaznikId = polaznikId; }

    public Long getRadionicaId() { return radionicaId; }
    public void setRadionicaId(Long radionicaId) { this.radionicaId = radionicaId; }

    public StatusPrisustva getStatus() { return status; }
    public void setStatus(StatusPrisustva status) { this.status = status; }

}




