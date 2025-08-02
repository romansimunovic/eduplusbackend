package com.edukatorplus.controller;

import com.edukatorplus.DTO.PolaznikDTO;
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
    public List<PolaznikDTO> getAll() {
        return polaznikService.getAll();
    }

    @GetMapping("/{id}")
    public PolaznikDTO getById(@PathVariable Long id) {
        return polaznikService.getById(id);
    }

    @PostMapping
    public PolaznikDTO create(@RequestBody PolaznikDTO dto) {
        return polaznikService.create(dto);
    }

    @PutMapping("/{id}")
    public PolaznikDTO update(@PathVariable Long id, @RequestBody PolaznikDTO dto) {
        return polaznikService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        polaznikService.delete(id);
    }
}
