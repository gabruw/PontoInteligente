package com.inteligente.ponto.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.inteligente.ponto.entities.Funcionario;
import com.inteligente.ponto.services.FuncionarioService;
import com.inteligente.ponto.repositories.FuncionarioRepository;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		log.info("Buscando funcioário pelo CPF {}", cpf);
		return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorAutorizacaoId(long id) {
		log.info("Buscando funcioário pelo Id da autorização {}", id);
		return Optional.ofNullable(this.funcionarioRepository.findByAutorizacaoId(id));
	}

	@Override
	public Funcionario persistir(Funcionario funcionario) {
		log.info("Persistindo funcionário: {}", funcionario);
		return this.funcionarioRepository.save(funcionario);
	}
}