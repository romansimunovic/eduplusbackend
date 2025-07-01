package com.edukatorplus.controller;

import com.edukatorplus.dto.RadionicaDTO;
import com.edukatorplus.service.RadionicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/radionice")
@Tag(name = "Radionica", description = "API za upravljanje radionicama")
public class RadionicaController {

    @Autowired
    private RadionicaService radionicaService;

    @GetMapping
    @Operation(summary = "Dohvati sve radionice")
    public List<RadionicaDTO> getAllRadionice() {
        return radionicaService.getAllRadionice();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Dohvati radionicu prema ID-u")
    public RadionicaDTO getRadionica(@PathVariable Long id) {
        return radionicaService.getRadionica(id);
    }

    @PostMapping
    @Operation(summary = "Kreiraj novu radionicu")
    public RadionicaDTO createRadionica(@RequestBody RadionicaDTO dto) {
        return radionicaService.saveRadionica(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ažuriraj radionicu prema ID-u")
    public RadionicaDTO updateRadionica(@PathVariable Long id, @RequestBody RadionicaDTO dto) {
        return radionicaService.updateRadionica(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Obriši radionicu prema ID-u")
    public void deleteRadionica(@PathVariable Long id) {
        radionicaService.deleteRadionica(id);
    }


}
