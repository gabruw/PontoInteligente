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

import com.inteligente.ponto.entities.Empresa;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class EmpresaRepositoryTest {
	@Autowired
	private EmpresaRepository empresaRepository;

	private static final String CNPJ = "51463645000100";

	@Before
	public void setUp() throws Exception {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de Exemplo");
		empresa.setCnpj(CNPJ);

		this.empresaRepository.save(empresa);
	}

	@After
	public final void tearDown() throws Exception {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarProCnpj() {
		Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);
		assertEquals(CNPJ, empresa.getCnpj());
	}
}