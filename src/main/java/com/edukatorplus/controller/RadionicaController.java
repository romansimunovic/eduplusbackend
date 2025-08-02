package com.edukatorplus.controller;

import com.edukatorplus.dto.RadionicaDTO;
import com.edukatorplus.service.RadionicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Uspješno dohvaćene radionice"),
        @ApiResponse(responseCode = "500", description = "Greška na serveru")
    })
    public ResponseEntity<List<RadionicaDTO>> getAllRadionice() {
        try {
            return ResponseEntity.ok(radionicaService.getAllRadionice());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Dohvati radionicu prema ID-u")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Radionica pronađena"),
        @ApiResponse(responseCode = "404", description = "Radionica nije pronađena")
    })
    public ResponseEntity<RadionicaDTO> getRadionica(@PathVariable Long id) {
        try {
            RadionicaDTO dto = radionicaService.getRadionica(id);
            if (dto != null) {
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @Operation(summary = "Kreiraj novu radionicu")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Radionica uspješno kreirana"),
        @ApiResponse(responseCode = "400", description = "Neispravan zahtjev")
    })
    public ResponseEntity<RadionicaDTO> createRadionica(@RequestBody RadionicaDTO dto) {
        try {
            RadionicaDTO saved = radionicaService.saveRadionica(dto);
            return ResponseEntity.status(201).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ažuriraj radionicu prema ID-u")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Radionica ažurirana"),
        @ApiResponse(responseCode = "404", description = "Radionica nije pronađena")
    })
    public ResponseEntity<RadionicaDTO> updateRadionica(@PathVariable Long id, @RequestBody RadionicaDTO dto) {
        try {
            RadionicaDTO updated = radionicaService.updateRadionica(id, dto);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Obriši radionicu prema ID-u")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Radionica obrisana"),
        @ApiResponse(responseCode = "404", description = "Radionica nije pronađena"),
        @ApiResponse(responseCode = "500", description = "Greška na serveru")
    })
    public ResponseEntity<Void> deleteRadionica(@PathVariable Long id) {
        try {
            boolean deleted = radionicaService.deleteRadionica(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
