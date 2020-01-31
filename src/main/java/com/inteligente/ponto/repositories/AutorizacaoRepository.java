package com.inteligente.ponto.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.inteligente.ponto.entities.Autorizacao;

@Repository
public interface AutorizacaoRepository extends JpaRepository<Autorizacao, Long> {
	@Transactional(readOnly = true)
	Autorizacao findByEmail(String email);
}