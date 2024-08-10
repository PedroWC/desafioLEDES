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

    @GetMapping
    public List<InstituicaoBrasileira> getAllInstituicoesBrasileiras() {
        return instituicaoBrasileiraService.getAllInstituicoesBrasileiras();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoBrasileira> getInstituicaoBrasileiraById(
            @PathVariable Long id) {
        Optional<InstituicaoBrasileira> instituicao = instituicaoBrasileiraService.getInstituicaoBrasileiraById(id);
        return instituicao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstituicaoBrasileira(
            @PathVariable Long id) {
        try {
            instituicaoBrasileiraService.deleteInstituicaoBrasileira(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
