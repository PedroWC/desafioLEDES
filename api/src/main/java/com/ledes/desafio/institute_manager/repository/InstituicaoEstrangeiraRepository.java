package com.ledes.desafio.institute_manager.repository;

import com.ledes.desafio.institute_manager.model.Instituicao;
import com.ledes.desafio.institute_manager.model.InstituicaoEstrangeira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstituicaoEstrangeiraRepository extends JpaRepository<InstituicaoEstrangeira, Long> {

    Optional<InstituicaoEstrangeira> findByInstituicao(Instituicao instituicao);

}
