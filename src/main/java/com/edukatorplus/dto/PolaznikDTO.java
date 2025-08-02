package com.edukatorplus.dto;

public record PolaznikDTO(
        Long id,
        String ime,
        String prezime,
        String email,
        Integer godinaRodenja,
        String spol,
        String telefon,
        String grad,
        String status
) {}
