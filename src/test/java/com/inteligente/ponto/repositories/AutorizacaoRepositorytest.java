package com.inteligente.ponto.repositories;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

import com.inteligente.ponto.entities.Autorizacao;
import com.inteligente.ponto.utils.PasswordUtils;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class AutorizacaoRepositorytest {
	@Autowired
	private AutorizacaoRepository autorizacaoRepository;

	private static final String EMAIL = "email@email.com";
	private static final String SENHA = "ABC@CDE_123";

	@Before
	public void setUp() throws Exception {
		Autorizacao autorizacao = new Autorizacao();
		autorizacao.setEmail(EMAIL);
		autorizacao.setSenha(PasswordUtils.gerarBCrypt(SENHA));

		this.autorizacaoRepository.save(autorizacao);
	}

	@After
	public final void tearDown() throws Exception {
		this.autorizacaoRepository.deleteAll();
	}

	@Test
	public void testBuscarFuncionarioPorEmail() {
		Autorizacao autorizacao = this.autorizacaoRepository.findByEmail(EMAIL);
		assertEquals(EMAIL, autorizacao.getEmail());
	}
}