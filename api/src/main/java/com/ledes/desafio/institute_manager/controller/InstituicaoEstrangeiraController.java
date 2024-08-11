package com.ledes.desafio.institute_manager.controller;

import com.ledes.desafio.institute_manager.model.InstituicaoEstrangeira;
import com.ledes.desafio.institute_manager.service.InstituicaoEstrangeiraService;
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
@RequestMapping("/api/instituicao/estrangeira")
@Tag(name = "Instituicao Estrangeira", description = "API para gerenciar instituições estrangeiras")
public class InstituicaoEstrangeiraController {

    private final InstituicaoEstrangeiraService instituicaoEstrangeiraService;

    @Autowired
    public InstituicaoEstrangeiraController(InstituicaoEstrangeiraService instituicaoEstrangeiraService) {
        this.instituicaoEstrangeiraService = instituicaoEstrangeiraService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova Instituição Estrangeira", description = "Cria uma nova instituição estrangeira e retorna os detalhes da instituição criada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Instituição estrangeira criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<InstituicaoEstrangeira> createInstituicaoEstrangeira(@RequestBody InstituicaoEstrangeira instituicaoEstrangeira) {
        InstituicaoEstrangeira createdInstituicao = instituicaoEstrangeiraService.saveInstituicaoEstrangeira(instituicaoEstrangeira);
        return ResponseEntity.status(201).body(createdInstituicao);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma Instituição Estrangeira", description = "Atualiza os detalhes de uma instituição estrangeira existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instituição estrangeira atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Instituição estrangeira não encontrada")
    })
    public ResponseEntity<InstituicaoEstrangeira> updateInstituicaoEstrangeira(@PathVariable Long id, @RequestBody InstituicaoEstrangeira instituicaoEstrangeira) {
        try {
            InstituicaoEstrangeira updatedInstituicao = instituicaoEstrangeiraService.updateInstituicaoEstrangeira(id, instituicaoEstrangeira);
            return ResponseEntity.ok(updatedInstituicao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
