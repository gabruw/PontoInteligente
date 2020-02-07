package com.inteligente.ponto.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inteligente.ponto.dto.LancamentoDTO;
import com.inteligente.ponto.entities.Funcionario;
import com.inteligente.ponto.entities.Lancamento;
import com.inteligente.ponto.enums.TipoEnum;
import com.inteligente.ponto.services.FuncionarioService;
import com.inteligente.ponto.services.LancamentoService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class LancamentoControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private LancamentoService lancamentoService;

	@MockBean
	private FuncionarioService funcionarioService;

	private static final String URL_BASE = "/api/lancamento/";
	private static final long ID_FUNCIONARIO = Long.valueOf(1);
	private static final long ID_LANCAMENTO = Long.valueOf(1);
	private static final String TIPO = TipoEnum.INICIO_TRABALHO.name();
	private static final Date DATA = new Date();

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	@WithMockUser
	public void testCadastrarLancamento() throws Exception {
		Lancamento lancamento = this.ObterDadosLancamento();
		BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.anyLong()))
				.willReturn(Optional.of(new Funcionario()));
		BDDMockito.given(this.lancamentoService.persistir(Mockito.any(Lancamento.class))).willReturn(lancamento);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE).content(this.ObterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_LANCAMENTO)).andExpect(jsonPath("$.data.tipo").value(TIPO))
				.andExpect(jsonPath("$.data.data").value(this.dateFormat.format(DATA)))
				.andExpect(jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Test
	@WithMockUser
	public void testCadastrarLancamentoFuncionarioIdInvalido() throws Exception {
		BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LANCAMENTO).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private String ObterJsonRequisicaoPost() throws JsonProcessingException {
		LancamentoDTO lancamentoDTO = new LancamentoDTO();
		lancamentoDTO.setId(null);
		lancamentoDTO.setData(this.dateFormat.format(DATA));
		lancamentoDTO.setTipo(TIPO);
		lancamentoDTO.setFuncionarioId(ID_FUNCIONARIO);
		ObjectMapper mapper = new ObjectMapper();

		return mapper.writeValueAsString(lancamentoDTO);
	}

	private Lancamento ObterDadosLancamento() {
		Lancamento lancamento = new Lancamento();
		lancamento.setId(ID_LANCAMENTO);
		lancamento.setData(DATA);
		lancamento.setTipo(TipoEnum.valueOf(TIPO));
		lancamento.setFuncionario(new Funcionario());
		lancamento.getFuncionario().setId(ID_FUNCIONARIO);

		return lancamento;
	}
}