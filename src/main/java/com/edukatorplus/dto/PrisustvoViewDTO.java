package com.edukatorplus.dto;

import com.edukatorplus.model.StatusPrisustva;

public record PrisustvoViewDTO(
    Long id,
    Long polaznikId,
    Long radionicaId,
    String polaznikImePrezime,
    String radionicaNaziv,
    StatusPrisustva status,
    String rodnoOsjetljivTekst
) {}