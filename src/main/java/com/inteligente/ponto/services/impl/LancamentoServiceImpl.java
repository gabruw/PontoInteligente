package com.inteligente.ponto.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import com.inteligente.ponto.entities.Lancamento;
import com.inteligente.ponto.services.LancamentoService;
import com.inteligente.ponto.repositories.LancamentoRepository;

@Service
public class LancamentoServiceImpl implements LancamentoService {
	private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Override
	public Page<Lancamento> buscarPorFuncionarioId(long funcionarioId, PageRequest pageRequest) {
		log.info("Buscando lançamentos para o Id do funcionário {}", funcionarioId);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	@Override
	public Optional<Lancamento> buscarPorId(long id) {
		log.info("Buscando lançamentos pelo Id {}", id);
		return this.lancamentoRepository.findById(id);
	}

	@Override
	public Lancamento persistir(Lancamento lancamento) {
		log.info("Persistindo lançamento: {}", lancamento);
		return this.lancamentoRepository.save(lancamento);
	}

	@Override
	public void remover(long id) {
		log.info("Removendo o lançamento Id {}", id);
		this.lancamentoRepository.deleteById(id);
	}
}