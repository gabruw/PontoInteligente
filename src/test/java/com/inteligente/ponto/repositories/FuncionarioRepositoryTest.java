package com.inteligente.ponto.repositories;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.NoSuchAlgorithmException;

import com.inteligente.ponto.enums.PerfilEnum;
import com.inteligente.ponto.utils.PasswordUtils;
import com.inteligente.ponto.entities.Empresa;
import com.inteligente.ponto.entities.Autorizacao;
import com.inteligente.ponto.entities.Funcionario;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class FuncionarioRepositoryTest {
	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private AutorizacaoRepository autorizacaoRepository;

	private static final String CPF = "24291173474";
	private static final String CNPJ = "51463645000100";
	private static final String EMAIL = "email@email.com";
	private static final String SENHA = "ABC@CDE_123";

	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		Autorizacao autorizacao = this.autorizacaoRepository.save(obterDadosAutorizacao());

		this.funcionarioRepository.save(obterDadosFuncionario(empresa, autorizacao));
	}

	@After
	public final void tearDown() throws Exception {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarFuncionarioPorCpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);
		assertEquals(CPF, funcionario.getCpf());
	}

	@Test
	public void testBuscarFuncionarioPorCpfInvalio() {
		Funcionario funcionario = this.funcionarioRepository.findByCpf("00000000000");
		assertNotNull(funcionario);
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de Exemplo");
		empresa.setCnpj(CNPJ);

		return empresa;
	}

	private Autorizacao obterDadosAutorizacao() {
		Autorizacao autorizacao = new Autorizacao();
		autorizacao.setEmail(EMAIL);
		autorizacao.setSenha(PasswordUtils.gerarBCrypt(SENHA));

		return autorizacao;
	}

	private Funcionario obterDadosFuncionario(Empresa empresa, Autorizacao autorizacao)
			throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Fulano");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setCpf(CPF);
		funcionario.setLogin(autorizacao);
		funcionario.setEmpresa(empresa);

		return funcionario;
	}
}