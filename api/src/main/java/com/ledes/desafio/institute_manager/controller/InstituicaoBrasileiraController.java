package com.ledes.desafio.institute_manager.controller;

import com.ledes.desafio.institute_manager.model.InstituicaoBrasileira;
import com.ledes.desafio.institute_manager.service.InstituicaoBrasileiraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instituicao/brasileira")
@Tag(name = "Instituicao Brasileira", description = "API para gerenciar instituições brasileiras")
public class InstituicaoBrasileiraController {

    private final InstituicaoBrasileiraService instituicaoBrasileiraService;

    @Autowired
    public InstituicaoBrasileiraController(InstituicaoBrasileiraService instituicaoBrasileiraService) {
        this.instituicaoBrasileiraService = instituicaoBrasileiraService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova Instituição Brasileira", description = "Cria uma nova instituição brasileira e retorna os detalhes da instituição criada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Instituição brasileira criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<InstituicaoBrasileira> createInstituicaoBrasileira(
            @RequestBody InstituicaoBrasileira instituicaoBrasileira) {
        InstituicaoBrasileira createdInstituicao = instituicaoBrasileiraService.saveInstituicaoBrasileira(instituicaoBrasileira);
        return ResponseEntity.status(201).body(createdInstituicao);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma Instituição Brasileira", description = "Atualiza os detalhes de uma instituição brasileira existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instituição brasileira atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Instituição brasileira não encontrada")
    })
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
