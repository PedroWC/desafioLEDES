package com.ledes.desafio.institute_manager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "instituicao")
@Schema(description = "Representa uma instituição no sistema")
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da instituição", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false, length = 32)
    @Schema(description = "Nome da instituição", example = "Universidade Federal", maxLength = 32, required = true)
    private String nome;

    @Column(nullable = false, length = 8)
    @Schema(description = "Sigla da instituição", example = "UFMS", maxLength = 8, required = true)
    private String sigla;

    @Column(nullable = false)
    @Schema(description = "Status da instituição (ativa/inativa)", example = "true", defaultValue = "true", required = true)
    private Boolean status = true;
}
