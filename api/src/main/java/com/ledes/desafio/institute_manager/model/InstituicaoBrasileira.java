package com.ledes.desafio.institute_manager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
@Table(name = "instituicao_brasileira")
@Schema(description = "Representa uma instituição brasileira no sistema")
public class InstituicaoBrasileira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da instituição brasileira", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "instituicao_id", nullable = false)
    @Schema(description = "Referência à instituição geral associada", required = true)
    private Instituicao instituicao;

    @Column(nullable = false, length = 100)
    @Schema(description = "País da instituição, sempre 'Brasil' para instituições brasileiras", example = "Brasil", required = true)
    private String pais = "Brasil";

    @Column(nullable = false, length = 14)
    @Schema(description = "CNPJ da instituição brasileira", example = "12.345.678/0001-99", maxLength = 14, required = true)
    private String cnpj;

    @Column(nullable = false, length = 9)
    @Schema(description = "CEP da instituição brasileira", example = "12345-678", maxLength = 9, required = true)
    private String cep;

    @Column(nullable = false, length = 32)
    @Schema(description = "Logradouro da instituição brasileira", example = "Rua Exemplo", maxLength = 32, required = true)
    private String logradouro;

    @Column(nullable = false, length = 32)
    @Schema(description = "Bairro da instituição brasileira", example = "Centro", maxLength = 32, required = true)
    private String bairro;

    @Column(nullable = false, length = 32)
    @Schema(description = "Estado da instituição brasileira", example = "São Paulo", maxLength = 32, required = true)
    private String estado;

    @Column(nullable = false, length = 32)
    @Schema(description = "Município da instituição brasileira", example = "São Paulo", maxLength = 32, required = true)
    private String municipio;

    @Column(nullable = false, length = 8)
    @Schema(description = "Número do endereço da instituição brasileira", example = "123", maxLength = 8, required = true)
    private String numero;

    @Column(length = 16)
    @Schema(description = "Complemento do endereço da instituição brasileira", example = "Apto 101", maxLength = 16)
    private String complemento;
}
