package com.ledes.desafio.institute_manager.controller;

import com.ledes.desafio.institute_manager.dto.InstituicaoDetalhada;
import com.ledes.desafio.institute_manager.service.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instituicao")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;

    @Autowired
    public InstituicaoController(InstituicaoService instituicaoService) {
        this.instituicaoService = instituicaoService;
    }

    @GetMapping
    public ResponseEntity<List<InstituicaoDetalhada>> getAllInstituicoes() {
        List<InstituicaoDetalhada> instituicoes = instituicaoService.getAllInstituicoes();
        return ResponseEntity.ok(instituicoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoDetalhada> findInstituicaoById(@PathVariable Long id) {
        Optional<InstituicaoDetalhada> instituicaoDetalhada = instituicaoService.findInstituicaoById(id);
        return instituicaoDetalhada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
