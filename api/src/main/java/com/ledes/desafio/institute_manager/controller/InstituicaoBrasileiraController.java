package com.ledes.desafio.institute_manager.controller;

import com.ledes.desafio.institute_manager.model.InstituicaoBrasileira;
import com.ledes.desafio.institute_manager.service.InstituicaoBrasileiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instituicao/brasileira")
public class InstituicaoBrasileiraController {

    private final InstituicaoBrasileiraService instituicaoBrasileiraService;

    @Autowired
    public InstituicaoBrasileiraController(InstituicaoBrasileiraService instituicaoBrasileiraService) {
        this.instituicaoBrasileiraService = instituicaoBrasileiraService;
    }

    @PostMapping
    public ResponseEntity<InstituicaoBrasileira> createInstituicaoBrasileira(
            @RequestBody InstituicaoBrasileira instituicaoBrasileira) {
        InstituicaoBrasileira createdInstituicao = instituicaoBrasileiraService.saveInstituicaoBrasileira(instituicaoBrasileira);
        return ResponseEntity.status(201).body(createdInstituicao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoBrasileira> updateInstituicaoBrasileira(
            @PathVariable Long id,
            @RequestBody InstituicaoBrasileira instituicaoBrasileira) {
        try {
            InstituicaoBrasileira updatedInstituicao = instituicaoBrasileiraService.updateInstituicaoBrasileira(id, instituicaoBrasileira);
            return ResponseEntity.ok(updatedInstituicao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
