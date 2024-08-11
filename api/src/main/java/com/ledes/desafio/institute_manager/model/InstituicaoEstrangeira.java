package com.ledes.desafio.institute_manager.model;

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
public class InstituicaoEstrangeira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "instituicao_id", nullable = false)
    private Instituicao instituicao;

    @Column(nullable = false, length = 100)
    private String pais;

    @Column(length = 9)
    private String cep;

    @Column(nullable = false, length = 32)
    private String logradouro;

    @Column(name = "estado_regiao", nullable = false, length = 32)
    private String estadoRegiao;

    @Column(nullable = false, length = 32)
    private String municipio;

    @Column(length = 32)
    private String complemento;
}
