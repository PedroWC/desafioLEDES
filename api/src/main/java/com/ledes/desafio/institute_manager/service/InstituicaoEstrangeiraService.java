package com.ledes.desafio.institute_manager.service;

import com.ledes.desafio.institute_manager.model.Instituicao;
import com.ledes.desafio.institute_manager.model.InstituicaoEstrangeira;
import com.ledes.desafio.institute_manager.repository.InstituicaoEstrangeiraRepository;
import com.ledes.desafio.institute_manager.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Serviço responsável por gerir as operações relacionadas às instituições estrangeiras.
 */
@Service
public class InstituicaoEstrangeiraService {

    private final InstituicaoEstrangeiraRepository instituicaoEstrangeiraRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final InstituicaoService instituicaoService;
    private static final Pattern CEP_PATTERN = Pattern.compile("\\d{1,9}");

    /**
     * Construtor para injeção das dependências necessárias.
     *
     * @param instituicaoEstrangeiraRepository Repositório para operações de CRUD de InstituicaoEstrangeira.
     * @param instituicaoService Serviço para operações comuns a todas as instituições.
     */
    @Autowired
    public InstituicaoEstrangeiraService(InstituicaoEstrangeiraRepository instituicaoEstrangeiraRepository, InstituicaoRepository instituicaoRepository, InstituicaoService instituicaoService) {
        this.instituicaoEstrangeiraRepository = instituicaoEstrangeiraRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.instituicaoService = instituicaoService;
    }

    /**
     * Salva uma nova instituição estrangeira no banco de dados.
     * Realiza validações do nome, sigla, CEP (se fornecido), e campos obrigatórios.
     *
     * @param instituicaoEstrangeira Instituição estrangeira a ser salva.
     * @return A InstituicaoEstrangeira salva.
     */
    public InstituicaoEstrangeira saveInstituicaoEstrangeira(InstituicaoEstrangeira instituicaoEstrangeira) {
        instituicaoService.validateNome(instituicaoEstrangeira.getInstituicao().getNome());
        instituicaoService.validateSigla(instituicaoEstrangeira.getInstituicao().getSigla());
        validateInstituicaoEstrangeira(instituicaoEstrangeira);

        Instituicao instituicao = instituicaoEstrangeira.getInstituicao();
        Instituicao savedInstituicao = instituicaoRepository.save(instituicao);

        instituicaoEstrangeira.setInstituicao(savedInstituicao);

        return instituicaoEstrangeiraRepository.save(instituicaoEstrangeira);
    }

    /**
     * Atualiza uma instituição estrangeira existente.
     * Verifica se a instituição existe, se o país não é Brasil, e realiza as validações necessárias.
     *
     * @param id ‘ID’ da instituição estrangeira a ser atualizada.
     * @param updatedInstituicao Dados atualizados da instituição estrangeira.
     * @return A InstituicaoEstrangeira atualizada.
     */
    public InstituicaoEstrangeira updateInstituicaoEstrangeira(Long id, InstituicaoEstrangeira updatedInstituicao) {
        Optional<Instituicao> instituicaoOptional = instituicaoRepository.findById(id);

        if (instituicaoOptional.isPresent()) {
            Instituicao instituicao = instituicaoOptional.get();
            Optional<InstituicaoEstrangeira> instituicaoEstrangeiraOptional = instituicaoEstrangeiraRepository.findByInstituicao(instituicao);

            if (instituicaoEstrangeiraOptional.isPresent()) {
                InstituicaoEstrangeira existingInstituicao = instituicaoEstrangeiraOptional.get();

                // Garantir que o país não seja alterado para o Brasil
                if ("Brasil".equals(existingInstituicao.getPais())) {
                    throw new IllegalArgumentException("Não é possível alterar uma instituição estrangeira para brasileira.");
                }

                // Validações comuns
                instituicaoService.validateNome(updatedInstituicao.getInstituicao().getNome());
                instituicaoService.validateSigla(updatedInstituicao.getInstituicao().getSigla());

                // Validação do CEP (não obrigatório)
                if (updatedInstituicao.getCep() != null && !CEP_PATTERN.matcher(updatedInstituicao.getCep()).matches()) {
                    throw new IllegalArgumentException("O CEP deve ter no máximo 9 caracteres numéricos.");
                }

                // Validação de campos obrigatórios
                validateCamposObrigatorios(updatedInstituicao);

                // Atualizando os campos permitidos
                existingInstituicao.getInstituicao().setNome(updatedInstituicao.getInstituicao().getNome());
                existingInstituicao.getInstituicao().setSigla(updatedInstituicao.getInstituicao().getSigla());
                existingInstituicao.setCep(updatedInstituicao.getCep());
                existingInstituicao.setLogradouro(updatedInstituicao.getLogradouro());
                existingInstituicao.setEstadoRegiao(updatedInstituicao.getEstadoRegiao());
                existingInstituicao.setMunicipio(updatedInstituicao.getMunicipio());
                existingInstituicao.setComplemento(updatedInstituicao.getComplemento());

                return instituicaoEstrangeiraRepository.save(existingInstituicao);
            } else {
                throw new IllegalArgumentException("Instituição não encontrada.");
            }
        } else {
            throw new IllegalArgumentException("Instituição não encontrada.");
        }
    }

    /**
     * Valida os campos obrigatórios de uma instituição estrangeira.
     *
     * @param instituicaoEstrangeira Instituição estrangeira a ser validada.
     */
    private void validateCamposObrigatorios(InstituicaoEstrangeira instituicaoEstrangeira) {
        if (!StringUtils.hasText(instituicaoEstrangeira.getLogradouro()) || instituicaoEstrangeira.getLogradouro().length() > 32) {
            throw new IllegalArgumentException("O logradouro deve ter no máximo 32 caracteres e não pode ser vazio.");
        }
        if (!StringUtils.hasText(instituicaoEstrangeira.getEstadoRegiao()) || instituicaoEstrangeira.getEstadoRegiao().length() > 32) {
            throw new IllegalArgumentException("O estado/região deve ter no máximo 32 caracteres e não pode ser vazio.");
        }
        if (!StringUtils.hasText(instituicaoEstrangeira.getMunicipio()) || instituicaoEstrangeira.getMunicipio().length() > 32) {
            throw new IllegalArgumentException("O município deve ter no máximo 32 caracteres e não pode ser vazio.");
        }

        // Validação do complemento (campo opcional)
        if (instituicaoEstrangeira.getComplemento() != null && instituicaoEstrangeira.getComplemento().length() > 32) {
            throw new IllegalArgumentException("O complemento deve ter no máximo 32 caracteres.");
        }
    }

    /**
     * Valida uma instituição estrangeira, verificando o país, CEP (se fornecido) e os campos obrigatórios.
     *
     * @param instituicaoEstrangeira Instituição estrangeira a ser validada.
     */
    private void validateInstituicaoEstrangeira(InstituicaoEstrangeira instituicaoEstrangeira) {
        // Validação do País
        if ("Brasil".equals(instituicaoEstrangeira.getPais())) {
            throw new IllegalArgumentException("O país não pode ser Brasil para uma Instituição Estrangeira.");
        }

        // Validação do CEP (não é obrigatório)
        if (instituicaoEstrangeira.getCep() != null && !CEP_PATTERN.matcher(instituicaoEstrangeira.getCep()).matches()) {
            throw new IllegalArgumentException("O CEP deve ter no máximo 9 caracteres numéricos.");
        }

        // Validação dos campos obrigatórios
        validateCamposObrigatorios(instituicaoEstrangeira);
    }
}
