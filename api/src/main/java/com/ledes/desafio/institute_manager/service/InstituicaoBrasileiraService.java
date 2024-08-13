package com.ledes.desafio.institute_manager.service;

import com.ledes.desafio.institute_manager.model.Instituicao;
import com.ledes.desafio.institute_manager.model.InstituicaoBrasileira;
import com.ledes.desafio.institute_manager.repository.InstituicaoBrasileiraRepository;
import com.ledes.desafio.institute_manager.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Serviço responsável por gerir as operações relacionadas às instituições brasileiras.
 */
@Service
public class InstituicaoBrasileiraService {

    private final InstituicaoBrasileiraRepository instituicaoBrasileiraRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final InstituicaoService instituicaoService;

    /**
     * Construtor para injeção das dependências necessárias.
     *
     * @param instituicaoBrasileiraRepository Repositório para operações de CRUD de InstituiçãoBrasileira.
     * @param instituicaoService Serviço para operações comuns a todas as instituições.
     */
    @Autowired
    public InstituicaoBrasileiraService(InstituicaoBrasileiraRepository instituicaoBrasileiraRepository, InstituicaoRepository instituicaoRepository, InstituicaoService instituicaoService) {
        this.instituicaoBrasileiraRepository = instituicaoBrasileiraRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.instituicaoService = instituicaoService;
    }

    /**
     * Salva uma nova instituição brasileira no banco de dados.
     * Realiza validações do nome, sigla, CNPJ, CEP, e campos obrigatórios.
     *
     * @param instituicaoBrasileira Instituição brasileira a ser salva.
     * @return A InstituiçãoBrasileira salva.
     */
    public InstituicaoBrasileira saveInstituicaoBrasileira(InstituicaoBrasileira instituicaoBrasileira) {
        instituicaoService.validateNome(instituicaoBrasileira.getInstituicao().getNome());
        instituicaoService.validateSigla(instituicaoBrasileira.getInstituicao().getSigla());
        validateInstituicaoBrasileira(instituicaoBrasileira);

        Instituicao instituicao = instituicaoBrasileira.getInstituicao();
        Instituicao savedInstituicao = instituicaoRepository.save(instituicao);

        instituicaoBrasileira.setInstituicao(savedInstituicao);

        return instituicaoBrasileiraRepository.save(instituicaoBrasileira);
    }

    /**
     * Atualiza uma instituição brasileira existente.
     * Verifica se a instituição existe, se o país é Brasil, e realiza as validações necessárias.
     *
     * @param id ‘ID’ da instituição brasileira a ser atualizada.
     * @param updatedInstituicao Dados atualizados da instituição brasileira.
     * @return A InstituiçãoBrasileira atualizada.
     */
    public InstituicaoBrasileira updateInstituicaoBrasileira(Long id, InstituicaoBrasileira updatedInstituicao) {
        Optional<Instituicao> instituicaoOptional = instituicaoRepository.findById(id);

        if (instituicaoOptional.isPresent()) {
            Instituicao instituicao = instituicaoOptional.get();

            Optional<InstituicaoBrasileira> instituicaoBrasileiraOptional = instituicaoBrasileiraRepository.findByInstituicao(instituicao);

            if (instituicaoBrasileiraOptional.isPresent()) {
                InstituicaoBrasileira existingInstituicao = instituicaoBrasileiraOptional.get();

                // Garantir que o país não seja alterado
                if (!"Brasil".equals(existingInstituicao.getPais())) {
                    throw new IllegalArgumentException("Não é possível alterar uma instituição brasileira para estrangeira.");
                }

                // Validações comuns
                instituicaoService.validateNome(updatedInstituicao.getInstituicao().getNome());
                instituicaoService.validateSigla(updatedInstituicao.getInstituicao().getSigla());

                // Validação de campos obrigatórios
                validateCamposObrigatorios(updatedInstituicao);

                // Atualizando os campos permitidos
                existingInstituicao.getInstituicao().setNome(updatedInstituicao.getInstituicao().getNome());
                existingInstituicao.getInstituicao().setSigla(updatedInstituicao.getInstituicao().getSigla());
                existingInstituicao.setCnpj(updatedInstituicao.getCnpj());
                existingInstituicao.setCep(updatedInstituicao.getCep());
                existingInstituicao.setLogradouro(updatedInstituicao.getLogradouro());
                existingInstituicao.setBairro(updatedInstituicao.getBairro());
                existingInstituicao.setEstado(updatedInstituicao.getEstado());
                existingInstituicao.setMunicipio(updatedInstituicao.getMunicipio());
                existingInstituicao.setNumero(updatedInstituicao.getNumero());
                existingInstituicao.setComplemento(updatedInstituicao.getComplemento());

                return instituicaoBrasileiraRepository.save(existingInstituicao);
            } else {
                throw new IllegalArgumentException("Instituição não encontrada.");
            }
        } else {
            throw new IllegalArgumentException("Instituição não encontrada.");
        }
    }

    /**
     * Valida os campos obrigatórios de uma instituição brasileira.
     *
     * @param instituicaoBrasileira Instituição brasileira a ser validada.
     */
    private void validateCamposObrigatorios(InstituicaoBrasileira instituicaoBrasileira) {
        if (!StringUtils.hasText(instituicaoBrasileira.getLogradouro()) || instituicaoBrasileira.getLogradouro().length() > 32) {
            throw new IllegalArgumentException("O logradouro deve ter no máximo 32 caracteres e não pode ser vazio.");
        }
        if (!StringUtils.hasText(instituicaoBrasileira.getBairro()) || instituicaoBrasileira.getBairro().length() > 32) {
            throw new IllegalArgumentException("O bairro deve ter no máximo 32 caracteres e não pode ser vazio.");
        }
        if (!StringUtils.hasText(instituicaoBrasileira.getEstado()) || instituicaoBrasileira.getEstado().length() > 32) {
            throw new IllegalArgumentException("O estado deve ter no máximo 32 caracteres e não pode ser vazio.");
        }
        if (!StringUtils.hasText(instituicaoBrasileira.getMunicipio()) || instituicaoBrasileira.getMunicipio().length() > 32) {
            throw new IllegalArgumentException("O município deve ter no máximo 32 caracteres e não pode ser vazio.");
        }
        if (!StringUtils.hasText(instituicaoBrasileira.getNumero()) || instituicaoBrasileira.getNumero().length() > 8) {
            throw new IllegalArgumentException("O número deve ter no máximo 8 caracteres e não pode ser vazio.");
        }

        if (instituicaoBrasileira.getComplemento() != null && instituicaoBrasileira.getComplemento().length() > 16) {
            throw new IllegalArgumentException("O complemento deve ter no máximo 16 caracteres.");
        }
    }

    /**
     * Valida uma instituição brasileira, verificando país, CNPJ, CEP e campos obrigatórios.
     *
     * @param instituicaoBrasileira Instituição brasileira a ser validada.
     */
    private void validateInstituicaoBrasileira(InstituicaoBrasileira instituicaoBrasileira) {
        if (!"Brasil".equals(instituicaoBrasileira.getPais())) {
            throw new IllegalArgumentException("O país deve ser Brasil para uma Instituição Brasileira.");
        }

        validateCamposObrigatorios(instituicaoBrasileira);
    }
}
