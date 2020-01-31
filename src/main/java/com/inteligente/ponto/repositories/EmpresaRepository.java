package com.inteligente.ponto.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.inteligente.ponto.entities.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	@Transactional(readOnly = true)
	Empresa findByCnpj(String cnpj);
}