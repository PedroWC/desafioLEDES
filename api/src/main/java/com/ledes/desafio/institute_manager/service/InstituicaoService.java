package com.ledes.desafio.institute_manager.service;

import com.ledes.desafio.institute_manager.model.Instituicao;
import com.ledes.desafio.institute_manager.repository.InstituicaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;

    @Autowired
    public InstituicaoService(InstituicaoRepository instituicaoRepository) {
        this.instituicaoRepository = instituicaoRepository;
    }

    public void inativarInstituicao(Long id) {
        Optional<Instituicao> optionalInstituicao = instituicaoRepository.findById(id);
        if (optionalInstituicao.isPresent()) {
            Instituicao instituicao = optionalInstituicao.get();
            instituicao.setStatus(false);
            instituicaoRepository.save(instituicao);
        } else {
            throw new EntityNotFoundException("Instituição não encontrada.");
        }
    }

    public void reativarInstituicao(Long id) {
        Optional<Instituicao> optionalInstituicao = instituicaoRepository.findById(id);
        if (optionalInstituicao.isPresent()) {
            Instituicao instituicao = optionalInstituicao.get();
            instituicao.setStatus(true);
            instituicaoRepository.save(instituicao);
        } else {
            throw new EntityNotFoundException("Instituição não encontrada.");
        }
    }

    public void validateNome(String nome) {
        if (!StringUtils.hasText(nome) || nome.length() > 32) {
            throw new IllegalArgumentException("O nome deve ter no máximo 32 caracteres e não pode ser vazio.");
        }
    }

    public void validateSigla(String sigla) {
        if (!StringUtils.hasText(sigla) || sigla.length() > 8) {
            throw new IllegalArgumentException("A sigla deve ter no máximo 8 caracteres e não pode ser vazia.");
        }
    }
}
