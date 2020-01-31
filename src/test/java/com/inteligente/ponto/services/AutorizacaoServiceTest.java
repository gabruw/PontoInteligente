package com.inteligente.ponto.services;

import java.util.Optional;

import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.BDDMockito;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.inteligente.ponto.entities.Autorizacao;
import com.inteligente.ponto.repositories.AutorizacaoRepository;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class AutorizacaoServiceTest {
	@MockBean
	private AutorizacaoRepository autorizacaoRepository;

	@Autowired
	private AutorizacaoService autorizacaoService;

	private static final String EMAIL = "email@email.com";

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.autorizacaoRepository.findByEmail(Mockito.anyString())).willReturn(new Autorizacao());
		BDDMockito.given(this.autorizacaoRepository.save(Mockito.any(Autorizacao.class))).willReturn(new Autorizacao());
	}

	@Test
	public void testBuscarAutorizacaoPorEmail() {
		Optional<Autorizacao> autorizacao = this.autorizacaoService.buscarPorEmail(EMAIL);
		assertTrue(autorizacao.isPresent());
	}

	@Test
	public void testPersistirAutorizacao() {
		Autorizacao autorizacao = this.autorizacaoService.persistir(new Autorizacao());
		assertNotNull(autorizacao);
	}
}