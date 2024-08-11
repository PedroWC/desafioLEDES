package com.ledes.desafio.institute_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstituicaoDetalhada {

    private Long id;
    private String nome;
    private String sigla;
    private Boolean status;
    private String pais;
    private String cep;
    private String logradouro;
    private String complemento;
    private String estado;
    private String municipio;
    private String cnpj;

    // Campos específicos brasileiros
    private String bairro;
    private String numero;
}
