package com.ledes.desafio.institute_manager.service;

import com.ledes.desafio.institute_manager.model.InstituicaoBrasileira;
import com.ledes.desafio.institute_manager.repository.InstituicaoBrasileiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class InstituicaoBrasileiraService {

    private final InstituicaoBrasileiraRepository instituicaoBrasileiraRepository;
    private final InstituicaoService instituicaoService;
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}");
    private static final Pattern CEP_PATTERN = Pattern.compile("\\d{5}-\\d{3}");

    @Autowired
    public InstituicaoBrasileiraService(InstituicaoBrasileiraRepository instituicaoBrasileiraRepository, InstituicaoService instituicaoService) {
        this.instituicaoBrasileiraRepository = instituicaoBrasileiraRepository;
        this.instituicaoService = instituicaoService;
    }

    public List<InstituicaoBrasileira> getAllInstituicoesBrasileiras() {
        return instituicaoBrasileiraRepository.findAll();
    }

    public Optional<InstituicaoBrasileira> getInstituicaoBrasileiraById(Long id) {
        return instituicaoBrasileiraRepository.findById(id);
    }

    public InstituicaoBrasileira saveInstituicaoBrasileira(InstituicaoBrasileira instituicaoBrasileira) {
        instituicaoService.validateNome(instituicaoBrasileira.getInstituicao().getNome());
        instituicaoService.validateSigla(instituicaoBrasileira.getInstituicao().getSigla());
        validateInstituicaoBrasileira(instituicaoBrasileira);
        return instituicaoBrasileiraRepository.save(instituicaoBrasileira);
    }

    public void deleteInstituicaoBrasileira(Long id) {
        instituicaoBrasileiraRepository.deleteById(id);
    }

    public InstituicaoBrasileira updateInstituicaoBrasileira(Long id, InstituicaoBrasileira updatedInstituicao) {
        Optional<InstituicaoBrasileira> existingInstituicaoOpt = instituicaoBrasileiraRepository.findById(id);

        if (existingInstituicaoOpt.isPresent()) {
            InstituicaoBrasileira existingInstituicao = existingInstituicaoOpt.get();

            // Garantir que o país não seja alterado
            if (!"Brasil".equals(existingInstituicao.getPais())) {
                throw new IllegalArgumentException("Não é possível alterar uma instituição brasileira para estrangeira.");
            }

            // Validações comuns
            instituicaoService.validateNome(updatedInstituicao.getInstituicao().getNome());
            instituicaoService.validateSigla(updatedInstituicao.getInstituicao().getSigla());

            // Validação do CNPJ
            if (!CNPJ_PATTERN.matcher(updatedInstituicao.getCnpj()).matches()) {
                throw new IllegalArgumentException("O CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX.");
            }

            // Validação do CEP
            if (!CEP_PATTERN.matcher(updatedInstituicao.getCep()).matches()) {
                throw new IllegalArgumentException("O CEP deve estar no formato XXXXX-XXX.");
            }

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
    }

    private void validateCamposObrigatorios(InstituicaoBrasileira instituicaoBrasileira) {
        // As validações específicas para uma Instituição Brasileira
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

        // Validação do complemento (campo opcional)
        if (instituicaoBrasileira.getComplemento() != null && instituicaoBrasileira.getComplemento().length() > 16) {
            throw new IllegalArgumentException("O complemento deve ter no máximo 16 caracteres.");
        }
    }

    private void validateInstituicaoBrasileira(InstituicaoBrasileira instituicaoBrasileira) {
        // Validação do País
        if (!"Brasil".equals(instituicaoBrasileira.getPais())) {
            throw new IllegalArgumentException("O país deve ser Brasil para uma Instituição Brasileira.");
        }

        // Validação do CNPJ
        if (!CNPJ_PATTERN.matcher(instituicaoBrasileira.getCnpj()).matches()) {
            throw new IllegalArgumentException("O CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX.");
        }

        // Validação do CEP
        if (!CEP_PATTERN.matcher(instituicaoBrasileira.getCep()).matches()) {
            throw new IllegalArgumentException("O CEP deve estar no formato XXXXX-XXX.");
        }

        // Validação dos campos obrigatórios
        validateCamposObrigatorios(instituicaoBrasileira);
    }
}
