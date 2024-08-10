package com.ledes.desafio.institute_manager.repository;

import com.ledes.desafio.institute_manager.model.InstituicaoBrasileira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituicaoBrasileiraRepository extends JpaRepository<InstituicaoBrasileira, Long> {

}
