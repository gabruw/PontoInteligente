package com.inteligente.ponto.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NamedQueries;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.inteligente.ponto.entities.Lancamento;

@Repository
@NamedQueries({
		@NamedQuery(name = "LancamentoRepository.findByFuncionarioId", query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionario.id = :funcionarioId") })
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
	@Transactional(readOnly = true)
	List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);

	@Transactional(readOnly = true)
	Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}