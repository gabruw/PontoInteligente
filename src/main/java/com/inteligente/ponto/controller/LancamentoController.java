package com.inteligente.ponto.controller;

import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.ObjectError;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.inteligente.ponto.enums.TipoEnum;
import com.inteligente.ponto.dto.LancamentoDTO;
import com.inteligente.ponto.response.Response;
import com.inteligente.ponto.entities.Lancamento;
import com.inteligente.ponto.entities.Funcionario;
import com.inteligente.ponto.services.LancamentoService;
import com.inteligente.ponto.services.FuncionarioService;

@RestController
@RequestMapping("/api/lancamento")
@CrossOrigin(origins = "*")
public class LancamentoController {
	private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private FuncionarioService funcionarioService;

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	public LancamentoController() {

	}

	@GetMapping(value = "/funcionario/{funcionarioId}")
	public ResponseEntity<Response<Page<LancamentoDTO>>> ListarPorFuncionarioId(
			@PathVariable("funcionarioId") long funcionarioId, @RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando lançamentos por Id do Funcionário: {}, página: {}", funcionarioId, pag);
		Response<Page<LancamentoDTO>> response = new Response<Page<LancamentoDTO>>();

		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Lancamento> lancamentos = this.lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest);
		Page<LancamentoDTO> lancamentosDTO = lancamentos.map(lanc -> this.ConverterLancamentoDTO(lanc));

		response.setData(lancamentosDTO);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDTO>> ListarPorId(@PathVariable("id") long id) {
		log.info("Buscando lançamento por Id: {}", id);
		Response<LancamentoDTO> response = new Response<LancamentoDTO>();
		Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Lançamento não encontrado parao Id: {}", id);
			response.getErrors().add("Lançamento não encontrado para o Id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.ConverterLancamentoDTO(lancamento.get()));
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/Adicionar")
	public ResponseEntity<Response<LancamentoDTO>> Adicionar(@Valid @RequestBody LancamentoDTO lancamentoDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando lançamento: {}", lancamentoDto.toString());
		Response<LancamentoDTO> response = new Response<LancamentoDTO>();

		this.ValidarFuncionario(lancamentoDto, result);
		Lancamento lancamento = this.ConverterDTOParaLancamento(lancamentoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando lançamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		lancamento = this.lancamentoService.persistir(lancamento);
		response.setData(this.ConverterLancamentoDTO(lancamento));
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDTO>> atualizar(@PathVariable("id") long id,
			@Valid @RequestBody LancamentoDTO lancamentoDTO, BindingResult result) throws ParseException {
		log.info("Atualizando lançamento: {}", lancamentoDTO.toString());
		Response<LancamentoDTO> response = new Response<LancamentoDTO>();

		this.ValidarFuncionario(lancamentoDTO, result);
		lancamentoDTO.setId(Optional.of(id));
		Lancamento lancamento = this.ConverterDTOParaLancamento(lancamentoDTO, result);

		if (result.hasErrors()) {
			log.error("Erro validando lançamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		lancamento = this.lancamentoService.persistir(lancamento);
		response.setData(this.ConverterLancamentoDTO(lancamento));
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") long id) {
		log.info("Remover lançamento por Id: {}", id);
		Response<String> response = new Response<String>();
		Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Erro ao remover devido ao lançamento Id: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover lançamento. Registro não encontrado para o Id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.lancamentoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}

	@SuppressWarnings("unused")
	private void ValidarFuncionario(LancamentoDTO lancamentoDTO, BindingResult result) {
		if (lancamentoDTO.getFuncionarioId() <= 0) {
			result.addError(new ObjectError("funcionario", "Funcionário não informado."));
			return;
		}

		log.info("Validando Id do Funcionário {}: ", lancamentoDTO.getFuncionarioId());
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(lancamentoDTO.getFuncionarioId());
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionário não encontrado. Id inexistente."));
		}
	}

	private LancamentoDTO ConverterLancamentoDTO(Lancamento lancamento) {
		LancamentoDTO lancamentoDTO = new LancamentoDTO();
		lancamentoDTO.setId(Optional.of(lancamento.getId()));
		lancamentoDTO.setData(this.dateFormat.format(lancamento.getData()));
		lancamentoDTO.setTipo(lancamento.getTipo().toString());
		lancamentoDTO.setDescricao(lancamento.getDescricao());
		lancamentoDTO.setLocalizacao(lancamento.getLocalizacao());
		lancamentoDTO.setFuncionarioId(lancamento.getFuncionario().getId());

		return lancamentoDTO;
	}

	private Lancamento ConverterDTOParaLancamento(LancamentoDTO lancamentoDTO, BindingResult result)
			throws ParseException {
		Lancamento lancamento = new Lancamento();

		if (lancamentoDTO.getId().isPresent()) {
			Optional<Lancamento> lanc = this.lancamentoService.buscarPorId(lancamentoDTO.getId().get());
			if (lanc.isPresent()) {
				lancamento = lanc.get();
			} else {
				result.addError(new ObjectError("lancamento", "Lançamento não encontrado."));
			}
		} else {
			lancamento.setFuncionario(new Funcionario());
			lancamento.getFuncionario().setId(lancamentoDTO.getFuncionarioId());
		}

		lancamento.setDescricao(lancamentoDTO.getDescricao());
		lancamento.setLocalizacao(lancamentoDTO.getLocalizacao());
		lancamento.setData(this.dateFormat.parse(lancamentoDTO.getData()));

		if (EnumUtils.isValidEnum(TipoEnum.class, lancamentoDTO.getTipo())) {
			lancamento.setTipo(TipoEnum.valueOf(lancamentoDTO.getTipo()));
		} else {
			result.addError(new ObjectError("tipo", "Tipo inválido."));
		}

		return lancamento;
	}
}