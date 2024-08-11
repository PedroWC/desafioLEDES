package com.ledes.desafio.institute_manager.service;

import com.ledes.desafio.institute_manager.dto.InstituicaoDetalhada;
import com.ledes.desafio.institute_manager.model.Instituicao;
import com.ledes.desafio.institute_manager.model.InstituicaoBrasileira;
import com.ledes.desafio.institute_manager.model.InstituicaoEstrangeira;
import com.ledes.desafio.institute_manager.repository.InstituicaoBrasileiraRepository;
import com.ledes.desafio.institute_manager.repository.InstituicaoEstrangeiraRepository;
import com.ledes.desafio.institute_manager.repository.InstituicaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;
    private final InstituicaoBrasileiraRepository instituicaoBrasileiraRepository;
    private final InstituicaoEstrangeiraRepository instituicaoEstrangeiraRepository;

    @Autowired
    public InstituicaoService(InstituicaoRepository instituicaoRepository, InstituicaoBrasileiraRepository instituicaoBrasileiraRepository, InstituicaoEstrangeiraRepository instituicaoEstrangeiraRepository) {
        this.instituicaoRepository = instituicaoRepository;
        this.instituicaoBrasileiraRepository = instituicaoBrasileiraRepository;
        this.instituicaoEstrangeiraRepository = instituicaoEstrangeiraRepository;
    }

    public List<InstituicaoDetalhada> getAllInstituicoes() {
        List<InstituicaoDetalhada> todasInstituicoesDetalhadas = new ArrayList<>();

        List<InstituicaoEstrangeira> instituicoesEstrangeiras = instituicaoEstrangeiraRepository.findAll();
        instituicoesEstrangeiras.forEach(ie -> todasInstituicoesDetalhadas.add(mapToInstituicaoDetalhada(ie)));

        List<InstituicaoBrasileira> instituicoesBrasileiras = instituicaoBrasileiraRepository.findAll();
        instituicoesBrasileiras.forEach(ib -> todasInstituicoesDetalhadas.add(mapToInstituicaoDetalhada(ib)));

        return todasInstituicoesDetalhadas;
    }

    public Optional<InstituicaoDetalhada> findInstituicaoById(Long id) {
        // Tenta encontrar na tabela de Instituicao Estrangeira
        Optional<InstituicaoEstrangeira> instituicaoEstrangeira = instituicaoEstrangeiraRepository.findById(id);
        if (instituicaoEstrangeira.isPresent()) {
            return Optional.of(mapToInstituicaoDetalhada(instituicaoEstrangeira.get()));
        }

        // Tenta encontrar na tabela de Instituicao Brasileira
        Optional<InstituicaoBrasileira> instituicaoBrasileira = instituicaoBrasileiraRepository.findById(id);
        return instituicaoBrasileira.map(this::mapToInstituicaoDetalhada);
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

    private void mapCommonFields(InstituicaoDetalhada instituicaoDetalhada,
                                 Instituicao instituicao, String pais, String cep,
                                 String logradouro, String estado,
                                 String municipio, String complemento) {
        instituicaoDetalhada.setId(instituicao.getId());
        instituicaoDetalhada.setNome(instituicao.getNome());
        instituicaoDetalhada.setStatus(instituicao.getStatus());
        instituicaoDetalhada.setPais(pais);
        instituicaoDetalhada.setCep(cep);
        instituicaoDetalhada.setLogradouro(logradouro);
        instituicaoDetalhada.setEstado(estado);
        instituicaoDetalhada.setMunicipio(municipio);
        instituicaoDetalhada.setComplemento(complemento);

    }

    private InstituicaoDetalhada mapToInstituicaoDetalhada(InstituicaoEstrangeira instituicaoEstrangeira) {
        InstituicaoDetalhada instituicaoDetalhada = new InstituicaoDetalhada();
        mapCommonFields(instituicaoDetalhada, instituicaoEstrangeira.getInstituicao(),
                instituicaoEstrangeira.getPais(), instituicaoEstrangeira.getCep(),
                instituicaoEstrangeira.getLogradouro(), instituicaoEstrangeira.getEstadoRegiao(),
                instituicaoEstrangeira.getMunicipio(), instituicaoEstrangeira.getComplemento());

        return instituicaoDetalhada;
    }

    private InstituicaoDetalhada mapToInstituicaoDetalhada(InstituicaoBrasileira instituicaoBrasileira) {
        InstituicaoDetalhada instituicaoDetalhada = new InstituicaoDetalhada();
        mapCommonFields(instituicaoDetalhada, instituicaoBrasileira.getInstituicao(),
                instituicaoBrasileira.getPais(), instituicaoBrasileira.getCep(),
                instituicaoBrasileira.getLogradouro(), instituicaoBrasileira.getEstado(),
                instituicaoBrasileira.getMunicipio(), instituicaoBrasileira.getComplemento());
        instituicaoDetalhada.setBairro(instituicaoBrasileira.getBairro());
        instituicaoDetalhada.setNumero(instituicaoBrasileira.getNumero());

        return instituicaoDetalhada;
    }
}
