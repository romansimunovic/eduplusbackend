package com.edukatorplus.dto;

import java.time.LocalDate;

public record RadionicaDTO(
    Long id,
    String naziv,
    LocalDate datum,
    String opis
) {}
