package com.inteligente.ponto.services;

import java.util.Optional;

import com.inteligente.ponto.entities.Funcionario;

public interface FuncionarioService {
	Optional<Funcionario> buscarPorCpf(String cpf);

	Optional<Funcionario> buscarPorAutorizacaoId(long id);

	Funcionario persistir(Funcionario funcionario);
}