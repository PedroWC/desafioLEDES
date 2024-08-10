package com.ledes.desafio.institute_manager.controller;

import com.ledes.desafio.institute_manager.model.InstituicaoEstrangeira;
import com.ledes.desafio.institute_manager.service.InstituicaoEstrangeiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instituicao/estrangeira")
public class InstituicaoEstrangeiraController {

    private final InstituicaoEstrangeiraService instituicaoEstrangeiraService;

    @Autowired
    public InstituicaoEstrangeiraController(InstituicaoEstrangeiraService instituicaoEstrangeiraService) {
        this.instituicaoEstrangeiraService = instituicaoEstrangeiraService;
    }

    @GetMapping
    public List<InstituicaoEstrangeira> getAllInstituicoesEstrangeiras() {
        return instituicaoEstrangeiraService.getAllInstituicoesEstrangeiras();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoEstrangeira> getInstituicaoEstrangeiraById(@PathVariable Long id) {
        Optional<InstituicaoEstrangeira> instituicao = instituicaoEstrangeiraService.getInstituicaoEstrangeiraById(id);
        return instituicao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InstituicaoEstrangeira> createInstituicaoEstrangeira(@RequestBody InstituicaoEstrangeira instituicaoEstrangeira) {
        InstituicaoEstrangeira createdInstituicao = instituicaoEstrangeiraService.saveInstituicaoEstrangeira(instituicaoEstrangeira);
        return ResponseEntity.status(201).body(createdInstituicao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoEstrangeira> updateInstituicaoEstrangeira(@PathVariable Long id, @RequestBody InstituicaoEstrangeira instituicaoEstrangeira) {
        try {
            InstituicaoEstrangeira updatedInstituicao = instituicaoEstrangeiraService.updateInstituicaoEstrangeira(id, instituicaoEstrangeira);
            return ResponseEntity.ok(updatedInstituicao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstituicaoEstrangeira(@PathVariable Long id) {
        try {
            instituicaoEstrangeiraService.deleteInstituicaoEstrangeira(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
