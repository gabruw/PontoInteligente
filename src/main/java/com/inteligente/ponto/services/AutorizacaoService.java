package com.inteligente.ponto.services;

import java.util.Optional;

import com.inteligente.ponto.entities.Autorizacao;

public interface AutorizacaoService {
	Optional<Autorizacao> buscarPorEmail(String email);
	
	Autorizacao persistir(Autorizacao autorizacao);
}