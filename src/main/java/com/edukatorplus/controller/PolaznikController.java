package com.edukatorplus.controller;

import com.edukatorplus.dto.PolaznikDTO;
import com.edukatorplus.service.PolaznikService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/polaznici")
@Tag(name = "Polaznik", description = "API za upravljanje polaznicima")
public class PolaznikController {

    @Autowired
    private PolaznikService polaznikService;

    @GetMapping
    @Operation(summary = "Dohvati sve polaznike", responses = {
            @ApiResponse(responseCode = "200", description = "Polaznici pronađeni"),
            @ApiResponse(responseCode = "404", description = "Polaznici nisu pronađeni")
    })
    public List<PolaznikDTO> getAll() {
        return polaznikService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Dohvati polaznika prema ID-u", responses = {
            @ApiResponse(responseCode = "200", description = "Polaznik pronađen"),
            @ApiResponse(responseCode = "404", description = "Polaznik s tim ID-om nije pronađen")
    })
    public PolaznikDTO getById(@PathVariable Long id) {
        PolaznikDTO polaznikDTO = polaznikService.getById(id);
        if (polaznikDTO == null) {
            throw new RuntimeException("Polaznik s ID-om " + id + " nije pronađen");
        }
        return polaznikDTO;
    }

    @PostMapping
    @Operation(summary = "Kreiraj novog polaznika", responses = {
            @ApiResponse(responseCode = "201", description = "Polaznik uspješno kreiran")
    })
    public PolaznikDTO create(@RequestBody PolaznikDTO dto) {
        return polaznikService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ažuriraj polaznika prema ID-u", responses = {
            @ApiResponse(responseCode = "200", description = "Polaznik uspješno ažuriran"),
            @ApiResponse(responseCode = "404", description = "Polaznik s tim ID-om nije pronađen")
    })
    public PolaznikDTO update(@PathVariable Long id, @RequestBody PolaznikDTO dto) {
        PolaznikDTO updatedPolaznik = polaznikService.update(id, dto);
        if (updatedPolaznik == null) {
            throw new RuntimeException("Polaznik s ID-om " + id + " nije pronađen");
        }
        return updatedPolaznik;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Obriši polaznika prema ID-u", responses = {
            @ApiResponse(responseCode = "200", description = "Polaznik uspješno obrisan"),
            @ApiResponse(responseCode = "404", description = "Polaznik s tim ID-om nije pronađen")
    })
    public void delete(@PathVariable Long id) {
        polaznikService.delete(id);
    }
}
