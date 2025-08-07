package com.edukatorplus.controller;

import com.edukatorplus.dto.PrisustvoDTO;
import com.edukatorplus.dto.PrisustvoViewDTO;
import com.edukatorplus.service.PrisustvoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prisustva")
@Tag(name = "Prisustvo", description = "API za upravljanje prisustvima polaznika na radionicama")
public class PrisustvoController {

    @Autowired
    private PrisustvoService prisustvoService;

    @GetMapping
    @Operation(summary = "Dohvati sva prisustva", responses = {
            @ApiResponse(responseCode = "200", description = "Prisustva pronađena")
    })
    public List<PrisustvoDTO> getAllPrisustva() {
        return prisustvoService.getAllPrisustva();
    }


       @GetMapping("/view/{radionicaId}")
    @Operation(summary = "Dohvati prikaz prisustava za određenu radionicu", responses = {
        @ApiResponse(responseCode = "200", description = "Formatirani podaci o prisustvima za radionicu")
})
public List<PrisustvoViewDTO> getByRadionica(@PathVariable Long radionicaId) {
    return prisustvoService.getAllForRadionica(radionicaId);


    @GetMapping("/view")
    @Operation(summary = "Dohvati prikaz prisustava (ime, radionica, status)", responses = {
            @ApiResponse(responseCode = "200", description = "Formatirani podaci o prisustvima")
    })
    public List<PrisustvoViewDTO> getAllPrisustvaView() {
        return prisustvoService.getAllForDisplay();
    }

    @PostMapping
    @Operation(summary = "Kreiraj novo prisustvo", responses = {
            @ApiResponse(responseCode = "201", description = "Prisustvo uspješno kreirano")
    })
    public PrisustvoDTO createPrisustvo(@RequestBody PrisustvoDTO prisustvoDTO) {
        return prisustvoService.savePrisustvo(prisustvoDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ažuriraj postojeće prisustvo", responses = {
            @ApiResponse(responseCode = "200", description = "Prisustvo ažurirano"),
            @ApiResponse(responseCode = "404", description = "Prisustvo nije pronađeno")
    })
    public PrisustvoDTO updatePrisustvo(@PathVariable Long id, @RequestBody PrisustvoDTO prisustvoDTO) {
        // Kreiramo novi DTO s id-om koji dolazi iz URL-a
        PrisustvoDTO dtoWithId = new PrisustvoDTO(
                id,
                prisustvoDTO.polaznikId(),
                prisustvoDTO.radionicaId(),
                prisustvoDTO.status()
        );
        return prisustvoService.updatePrisustvo(id, dtoWithId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Obriši prisustvo", responses = {
            @ApiResponse(responseCode = "200", description = "Prisustvo obrisano"),
            @ApiResponse(responseCode = "404", description = "Prisustvo nije pronađeno")
    })
    public void deletePrisustvo(@PathVariable Long id) {
        prisustvoService.deletePrisustvo(id);
    }
    
}
