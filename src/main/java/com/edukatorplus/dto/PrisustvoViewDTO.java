package com.edukatorplus.dto;

import com.edukatorplus.model.StatusPrisustva;

public record PrisustvoViewDTO(
        String polaznikImePrezime,
        String radionicaNaziv,
        StatusPrisustva status,
        String rodnoOsjetljivTekst // npr. "prisutna", "izostao", itd.
) {}
