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
@Table(name = "instituicao_brasileira")
public class InstituicaoBrasileira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "instituicao_id", nullable = false)
    private Instituicao instituicao;

    @Column(nullable = false, length = 100)
    private String pais = "Brasil";

    @Column(nullable = false, length = 14)
    private String cnpj;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = false, length = 32)
    private String logradouro;

    @Column(nullable = false, length = 32)
    private String bairro;

    @Column(nullable = false, length = 32)
    private String estado;

    @Column(nullable = false, length = 32)
    private String municipio;

    @Column(nullable = false, length = 8)
    private String numero;

    @Column(length = 16)
    private String complemento;
}
