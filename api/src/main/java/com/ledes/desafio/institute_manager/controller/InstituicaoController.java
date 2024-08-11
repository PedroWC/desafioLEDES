package com.ledes.desafio.institute_manager.controller;

import com.ledes.desafio.institute_manager.dto.InstituicaoDetalhada;
import com.ledes.desafio.institute_manager.service.InstituicaoService;
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
@RequestMapping("/api/instituicao")
@Tag(name = "Instituicao", description = "API para gerenciar instituições")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;

    @Autowired
    public InstituicaoController(InstituicaoService instituicaoService) {
        this.instituicaoService = instituicaoService;
    }

    @GetMapping
    @Operation(summary = "Obter todas as Instituições", description = "Retorna uma lista de todas as instituições, sejam brasileiras ou estrangeiras.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de instituições retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<InstituicaoDetalhada>> getAllInstituicoes() {
        List<InstituicaoDetalhada> instituicoes = instituicaoService.getAllInstituicoes();
        return ResponseEntity.ok(instituicoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter uma Instituição por ID", description = "Retorna os detalhes de uma instituição específica, seja brasileira ou estrangeira.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes da instituição retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Instituição não encontrada")
    })
    public ResponseEntity<InstituicaoDetalhada> findInstituicaoById(@PathVariable Long id) {
        Optional<InstituicaoDetalhada> instituicaoDetalhada = instituicaoService.findInstituicaoById(id);
        return instituicaoDetalhada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inativar uma Instituição", description = "Inativa uma instituição específica com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Instituição inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Instituição não encontrada")
    })
    public ResponseEntity<Void> inativarInstituicao(@PathVariable Long id) {
        try {
            instituicaoService.inativarInstituicao(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/reativar/{id}")
    @Operation(summary = "Reativar uma Instituição", description = "Reativa uma instituição inativa com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Instituição reativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Instituição não encontrada")
    })
    public ResponseEntity<Void> reativarInstituicao(@PathVariable Long id) {
        try {
            instituicaoService.reativarInstituicao(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
