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
}
