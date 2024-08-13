package com.ledes.desafio.institute_manager.repository;

import com.ledes.desafio.institute_manager.model.Instituicao;
import com.ledes.desafio.institute_manager.model.InstituicaoBrasileira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstituicaoBrasileiraRepository extends JpaRepository<InstituicaoBrasileira, Long> {

    Optional<InstituicaoBrasileira> findByInstituicao(Instituicao instituicao);

}
