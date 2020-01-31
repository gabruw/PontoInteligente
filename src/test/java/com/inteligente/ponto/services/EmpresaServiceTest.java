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

import com.inteligente.ponto.entities.Empresa;
import com.inteligente.ponto.repositories.EmpresaRepository;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class EmpresaServiceTest {
	@MockBean
	private EmpresaRepository empresaRepository;

	@Autowired
	private EmpresaService empresaService;

	private static final String CNPJ = "51463645000100";

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.empresaRepository.findByCnpj(Mockito.anyString())).willReturn(new Empresa());
		BDDMockito.given(this.empresaRepository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
	}

	@Test
	public void testBuscarEmpresaPorCnpj() {
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(CNPJ);
		assertTrue(empresa.isPresent());
	}

	@Test
	public void testPersistirEmpresa() {
		Empresa empresa = this.empresaService.persistir(new Empresa());
		assertNotNull(empresa);
	}
}