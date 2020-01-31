package com.inteligente.ponto.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.inteligente.ponto.entities.Lancamento;

public interface LancamentoService {
	Page<Lancamento> buscarPorFuncionarioId(long funcionarioId, PageRequest pageRequest);

	Optional<Lancamento> buscarPorId(long id);

	Lancamento persistir(Lancamento lancamento);

	void remover(long id);
}