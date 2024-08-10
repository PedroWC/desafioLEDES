package com.ledes.desafio.institute_manager.service;

import com.ledes.desafio.institute_manager.model.Instituicao;
import com.ledes.desafio.institute_manager.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;

    @Autowired
    public InstituicaoService(InstituicaoRepository instituicaoRepository) {
        this.instituicaoRepository = instituicaoRepository;
    }

    public List<Instituicao> getAllInstituicoes() {
        return instituicaoRepository.findAll();
    }

    public Optional<Instituicao> getInstituicaoById(Long id) {
        return instituicaoRepository.findById(id);
    }

    public Instituicao saveInstituicao(Instituicao instituicao) {
        validateNome(instituicao.getNome());
        validateSigla(instituicao.getSigla());
        return instituicaoRepository.save(instituicao);
    }

    public void deleteInstituicao(Long id) {
        instituicaoRepository.deleteById(id);
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
