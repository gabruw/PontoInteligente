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

import com.inteligente.ponto.entities.Funcionario;
import com.inteligente.ponto.repositories.FuncionarioRepository;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class FuncionarioServiceTest {
	@MockBean
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private FuncionarioService funcionarioService;

	private static final long ID = 1;
	private static final String CNPJ = "51463645000100";

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.findByAutorizacaoId(Mockito.anyLong()))
				.willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
	}

	@Test
	public void testBuscarFuncionarioId() {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(ID);
		assertTrue(funcionario.isPresent());
	}

	@Test
	public void testBuscarFuncionarioPorCnpj() {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorCpf(CNPJ);
		assertTrue(funcionario.isPresent());
	}

	@Test
	public void testBuscarFuncionarioPorAutorizacaoId() {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorAutorizacaoId(1);
		assertTrue(funcionario.isPresent());
	}

	@Test
	public void testPersisitrFuncionario() {
		Funcionario funcionario = this.funcionarioService.persistir(new Funcionario());
		assertNotNull(funcionario);
	}
}