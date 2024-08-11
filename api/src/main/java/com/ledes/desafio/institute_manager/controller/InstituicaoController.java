package com.ledes.desafio.institute_manager.controller;

import com.ledes.desafio.institute_manager.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instituicao")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;

    @Autowired
    public InstituicaoController(InstituicaoService instituicaoService) {
        this.instituicaoService = instituicaoService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarInstituicao(@PathVariable Long id) {
        try {
            instituicaoService.inativarInstituicao(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/reativar/{id}")
    public ResponseEntity<Void> reativarInstituicao(@PathVariable Long id) {
        try {
            instituicaoService.reativarInstituicao(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
