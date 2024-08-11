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
@Table(name = "instituicao_estrangeira")
@Schema(description = "Representa uma instituição estrangeira no sistema")
public class InstituicaoEstrangeira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único da instituição estrangeira", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "instituicao_id", nullable = false)
    @Schema(description = "Referência à instituição geral associada", required = true)
    private Instituicao instituicao;

    @Column(nullable = false, length = 100)
    @Schema(description = "País da instituição estrangeira", example = "Estados Unidos", required = true)
    private String pais;

    @Column(length = 9)
    @Schema(description = "CEP ou código postal da instituição estrangeira", example = "12345-678", maxLength = 9)
    private String cep;

    @Column(nullable = false, length = 32)
    @Schema(description = "Logradouro da instituição estrangeira", example = "Avenida Exemplo", maxLength = 32, required = true)
    private String logradouro;

    @Column(name = "estado_regiao", nullable = false, length = 32)
    @Schema(description = "Estado ou região da instituição estrangeira", example = "Califórnia", maxLength = 32, required = true)
    private String estadoRegiao;

    @Column(nullable = false, length = 32)
    @Schema(description = "Município da instituição estrangeira", example = "Los Angeles", maxLength = 32, required = true)
    private String municipio;

    @Column(length = 32)
    @Schema(description = "Complemento do endereço da instituição estrangeira", example = "Apartamento 501", maxLength = 32)
    private String complemento;
}
