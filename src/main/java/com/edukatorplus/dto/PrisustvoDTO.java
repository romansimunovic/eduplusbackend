package com.edukatorplus.dto;

import com.edukatorplus.model.StatusPrisustva;

public record PrisustvoDTO(Long id, Long polaznikId, Long radionicaId, StatusPrisustva status) {}
