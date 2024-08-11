package com.ledes.desafio.institute_manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Detalhes completos de uma instituição, incluindo campos específicos para instituições brasileiras e estrangeiras.")
public class InstituicaoDetalhada {

    @Schema(description = "ID da instituição", example = "1")
    private Long id;

    @Schema(description = "Nome da instituição", example = "Universidade Federal")
    private String nome;

    @Schema(description = "Sigla da instituição", example = "UF")
    private String sigla;

    @Schema(description = "Status da instituição (ativa ou inativa)", example = "true")
    private Boolean status;

    @Schema(description = "País da instituição", example = "Brasil")
    private String pais;

    @Schema(description = "CEP da instituição", example = "70000-000")
    private String cep;

    @Schema(description = "Logradouro da instituição", example = "Avenida Central")
    private String logradouro;

    @Schema(description = "Complemento do endereço da instituição", example = "Bloco A")
    private String complemento;

    @Schema(description = "Estado ou região da instituição", example = "Distrito Federal")
    private String estado;

    @Schema(description = "Município da instituição", example = "Brasília")
    private String municipio;

    @Schema(description = "CNPJ da instituição (aplicável apenas para instituições brasileiras)", example = "12.345.678/0001-99")
    private String cnpj;

    // Campos específicos brasileiros
    @Schema(description = "Bairro da instituição (aplicável apenas para instituições brasileiras)", example = "Asa Norte")
    private String bairro;

    @Schema(description = "Número do endereço da instituição (aplicável apenas para instituições brasileiras)", example = "1234")
    private String numero;
}
