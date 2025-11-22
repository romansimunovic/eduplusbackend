package com.edukatorplus.dto;
import java.time.LocalDate;

public class RadionicaDTO {
    private Long id;
    private String naziv;
    private LocalDate datum;
    private String opis;
    private String link;
    private String organizator;
    private String tip;
    private Integer maxPolaznika;
    private Boolean aktivna;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public LocalDate getDatum() { return datum; }
    public void setDatum(LocalDate datum) { this.datum = datum; }

    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getOrganizator() { return organizator; }
    public void setOrganizator(String organizator) { this.organizator = organizator; }

    public String getTip() { return tip; }
    public void setTip(String tip) { this.tip = tip; }

    public Integer getMaxPolaznika() { return maxPolaznika; }
    public void setMaxPolaznika(Integer maxPolaznika) { this.maxPolaznika = maxPolaznika; }

    public Boolean getAktivna() { return aktivna; }
    public void setAktivna(Boolean aktivna) { this.aktivna = aktivna; }
}
