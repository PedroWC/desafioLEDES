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

/**
 * Serviço responsável por gerenciar as operações relacionadas às instituições,
 * incluindo instituições brasileiras e estrangeiras.
 */
@Service
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;
    private final InstituicaoBrasileiraRepository instituicaoBrasileiraRepository;
    private final InstituicaoEstrangeiraRepository instituicaoEstrangeiraRepository;

    /**
     * Construtor para injeção das dependências necessárias.
     *
     * @param instituicaoRepository Repositório para operações de CRUD de Instituicao.
     * @param instituicaoBrasileiraRepository Repositório para operações de CRUD de InstituicaoBrasileira.
     * @param instituicaoEstrangeiraRepository Repositório para operações de CRUD de InstituicaoEstrangeira.
     */
    @Autowired
    public InstituicaoService(InstituicaoRepository instituicaoRepository,
                              InstituicaoBrasileiraRepository instituicaoBrasileiraRepository,
                              InstituicaoEstrangeiraRepository instituicaoEstrangeiraRepository) {
        this.instituicaoRepository = instituicaoRepository;
        this.instituicaoBrasileiraRepository = instituicaoBrasileiraRepository;
        this.instituicaoEstrangeiraRepository = instituicaoEstrangeiraRepository;
    }

    /**
     * Retorna uma lista de todas as instituições detalhadas, incluindo tanto brasileiras quanto estrangeiras.
     *
     * @return Lista de InstituicaoDetalhada.
     */
    public List<InstituicaoDetalhada> getAllInstituicoes() {
        List<InstituicaoDetalhada> todasInstituicoesDetalhadas = new ArrayList<>();

        List<InstituicaoEstrangeira> instituicoesEstrangeiras = instituicaoEstrangeiraRepository.findAll();
        instituicoesEstrangeiras.forEach(ie -> todasInstituicoesDetalhadas.add(mapToInstituicaoDetalhada(ie)));

        List<InstituicaoBrasileira> instituicoesBrasileiras = instituicaoBrasileiraRepository.findAll();
        instituicoesBrasileiras.forEach(ib -> todasInstituicoesDetalhadas.add(mapToInstituicaoDetalhada(ib)));

        return todasInstituicoesDetalhadas;
    }

    /**
     * Busca uma instituição pelo ID, retornando uma InstituicaoDetalhada.
     * Pode buscar tanto em InstituicaoBrasileira quanto em InstituicaoEstrangeira.
     *
     * @param id ID da instituição a ser buscada.
     * @return Optional contendo a InstituicaoDetalhada se encontrada, ou vazio se não encontrada.
     */
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

    /**
     * Inativa uma instituição, alterando seu status para false.
     *
     * @param id ID da instituição a ser inativada.
     */
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

    /**
     * Reativa uma instituição, alterando seu status para true.
     *
     * @param id ID da instituição a ser reativada.
     */
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

    /**
     * Valida o nome da instituição, garantindo que não seja vazio e tenha no máximo 32 caracteres.
     *
     * @param nome Nome da instituição a ser validado.
     */
    public void validateNome(String nome) {
        if (!StringUtils.hasText(nome) || nome.length() > 32) {
            throw new IllegalArgumentException("O nome deve ter no máximo 32 caracteres e não pode ser vazio.");
        }
    }

    /**
     * Valida a sigla da instituição, garantindo que não seja vazia e tenha no máximo 8 caracteres.
     *
     * @param sigla Sigla da instituição a ser validada.
     */
    public void validateSigla(String sigla) {
        if (!StringUtils.hasText(sigla) || sigla.length() > 8) {
            throw new IllegalArgumentException("A sigla deve ter no máximo 8 caracteres e não pode ser vazia.");
        }
    }

    /**
     * Mapeia campos comuns entre InstituicaoBrasileira e InstituicaoEstrangeira para InstituicaoDetalhada.
     *
     * @param instituicaoDetalhada Objeto onde os campos serão mapeados.
     * @param instituicao Objeto Instituicao com os dados a serem mapeados.
     * @param pais País da instituição.
     * @param cep CEP da instituição.
     * @param logradouro Logradouro da instituição.
     * @param estado Estado ou região da instituição.
     * @param municipio Município da instituição.
     * @param complemento Complemento do endereço da instituição.
     */
    private void mapCommonFields(InstituicaoDetalhada instituicaoDetalhada,
                                 Instituicao instituicao, String pais, String cep,
                                 String logradouro, String estado,
                                 String municipio, String complemento) {
        instituicaoDetalhada.setId(instituicao.getId());
        instituicaoDetalhada.setNome(instituicao.getNome());
        instituicaoDetalhada.setSigla(instituicao.getSigla());
        instituicaoDetalhada.setStatus(instituicao.getStatus());
        instituicaoDetalhada.setPais(pais);
        instituicaoDetalhada.setCep(cep);
        instituicaoDetalhada.setLogradouro(logradouro);
        instituicaoDetalhada.setEstado(estado);
        instituicaoDetalhada.setMunicipio(municipio);
        instituicaoDetalhada.setComplemento(complemento);

    }

    /**
     * Mapeia uma InstituicaoEstrangeira para uma InstituicaoDetalhada.
     *
     * @param instituicaoEstrangeira Instituição estrangeira a ser mapeada.
     * @return Objeto InstituicaoDetalhada com os dados mapeados.
     */
    private InstituicaoDetalhada mapToInstituicaoDetalhada(InstituicaoEstrangeira instituicaoEstrangeira) {
        InstituicaoDetalhada instituicaoDetalhada = new InstituicaoDetalhada();
        mapCommonFields(instituicaoDetalhada, instituicaoEstrangeira.getInstituicao(),
                instituicaoEstrangeira.getPais(), instituicaoEstrangeira.getCep(),
                instituicaoEstrangeira.getLogradouro(), instituicaoEstrangeira.getEstadoRegiao(),
                instituicaoEstrangeira.getMunicipio(), instituicaoEstrangeira.getComplemento());

        return instituicaoDetalhada;
    }

    /**
     * Mapeia uma InstituicaoBrasileira para uma InstituicaoDetalhada.
     *
     * @param instituicaoBrasileira Instituição brasileira a ser mapeada.
     * @return Objeto InstituicaoDetalhada com os dados mapeados.
     */
    private InstituicaoDetalhada mapToInstituicaoDetalhada(InstituicaoBrasileira instituicaoBrasileira) {
        InstituicaoDetalhada instituicaoDetalhada = new InstituicaoDetalhada();
        mapCommonFields(instituicaoDetalhada, instituicaoBrasileira.getInstituicao(),
                instituicaoBrasileira.getPais(), instituicaoBrasileira.getCep(),
                instituicaoBrasileira.getLogradouro(), instituicaoBrasileira.getEstado(),
                instituicaoBrasileira.getMunicipio(), instituicaoBrasileira.getComplemento());
        instituicaoDetalhada.setBairro(instituicaoBrasileira.getBairro());
        instituicaoDetalhada.setNumero(instituicaoBrasileira.getNumero());
        instituicaoDetalhada.setCnpj(instituicaoBrasileira.getCnpj());

        return instituicaoDetalhada;
    }
}
