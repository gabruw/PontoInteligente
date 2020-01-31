package com.inteligente.ponto.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NamedQueries;

import com.inteligente.ponto.entities.Funcionario;

@Repository
@NamedQueries({
		@NamedQuery(name = "FuncionarioRepository.findByAutorizacaoId", query = "SELECT func FROM Funcionario func WHERE func.autorizacao.id = :autorizacaoId") })
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	@Transactional(readOnly = true)
	Funcionario findByCpf(String cpf);

	@Transactional(readOnly = true)
	Funcionario findByAutorizacaoId(@Param("autorizacaoId") Long autorizacaoId);
}