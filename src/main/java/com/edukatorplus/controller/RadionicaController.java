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
    @Operation(summary = "Dohvati sve radionice", responses = {
            @ApiResponse(responseCode = "200", description = "Radionice pronađene"),
            @ApiResponse(responseCode = "404", description = "Nema radionica")
    })
    public List<RadionicaDTO> getAllRadionice() {
        return radionicaService.getAllRadionice();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Dohvati radionicu prema ID-u", responses = {
            @ApiResponse(responseCode = "200", description = "Radionica pronađena"),
            @ApiResponse(responseCode = "404", description = "Radionica s tim ID-om nije pronađena")
    })
    public RadionicaDTO getRadionica(@PathVariable Long id) {
        return radionicaService.getRadionica(id);
    }

    @PostMapping
    @Operation(summary = "Kreiraj novu radionicu", responses = {
            @ApiResponse(responseCode = "201", description = "Radionica uspješno kreirana")
    })
    public RadionicaDTO createRadionica(@RequestBody RadionicaDTO radionicaDTO) {
        return radionicaService.saveRadionica(radionicaDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ažuriraj radionicu prema ID-u", responses = {
            @ApiResponse(responseCode = "200", description = "Radionica uspješno ažurirana"),
            @ApiResponse(responseCode = "404", description = "Radionica s tim ID-om nije pronađena")
    })
    public RadionicaDTO updateRadionica(@PathVariable Long id, @RequestBody RadionicaDTO radionicaDTO) {
        return radionicaService.updateRadionica(id, radionicaDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Obriši radionicu prema ID-u", responses = {
            @ApiResponse(responseCode = "200", description = "Radionica uspješno obrisana"),
            @ApiResponse(responseCode = "404", description = "Radionica s tim ID-om nije pronađena")
    })
    public void deleteRadionica(@PathVariable Long id) {
        radionicaService.deleteRadionica(id);
    }
}
