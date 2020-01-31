package com.inteligente.ponto.repositories;

import java.util.Date;
import java.util.List;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.inteligente.ponto.enums.TipoEnum;
import com.inteligente.ponto.utils.PasswordUtils;
import com.inteligente.ponto.entities.Empresa;
import com.inteligente.ponto.enums.PerfilEnum;
import com.inteligente.ponto.entities.Lancamento;
import com.inteligente.ponto.entities.Autorizacao;
import com.inteligente.ponto.entities.Funcionario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class LancamentoRepositoryTest {
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private AutorizacaoRepository autorizacaoRepository;

	private long funcionarioId;

	private static final String CPF = "24291173474";
	private static final String CNPJ = "51463645000100";
	private static final String EMAIL = "email@email.com";
	private static final String SENHA = "ABC@CDE_123";

	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		Autorizacao autorizacao = this.autorizacaoRepository.save(obterDadosAutorizacao());
		
		Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa, autorizacao));
		this.funcionarioId = funcionario.getId();

		this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
		this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
	}

	@After
	public final void tearDown() throws Exception {
		this.lancamentoRepository.deleteAll();
	}

	@Test
	public void testBuscarLancamentosPorFuncionarioId() {
		List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
		assertEquals(2, lancamentos.size());
	}

	@Test
	public void testBuscarLancamentosPorFuncionarioIdPaginando() {
		PageRequest page = PageRequest.of(0, 10);
		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);

		assertEquals(2, lancamentos.getTotalElements());
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
	
	private Lancamento obterDadosLancamentos(Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		lancamento.setFuncionario(funcionario);

		return lancamento;
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