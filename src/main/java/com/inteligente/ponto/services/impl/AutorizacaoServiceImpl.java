package com.inteligente.ponto.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.inteligente.ponto.entities.Autorizacao;
import com.inteligente.ponto.services.AutorizacaoService;
import com.inteligente.ponto.repositories.AutorizacaoRepository;

@Service
public class AutorizacaoServiceImpl implements AutorizacaoService {
	private static final Logger log = LoggerFactory.getLogger(AutorizacaoServiceImpl.class);

	@Autowired
	private AutorizacaoRepository autorizacaoRepository;

	@Override
	public Optional<Autorizacao> buscarPorEmail(String email) {
		log.info("Buscando autorização por email {}", email);
		return Optional.ofNullable(autorizacaoRepository.findByEmail(email));
	}

	@Override
	public Autorizacao persistir(Autorizacao autorizacao) {
		log.info("Persistindo autorizacao: {}", autorizacao);
		return this.autorizacaoRepository.save(autorizacao);
	}
}