package com.inteligente.ponto.controller;

import java.util.Optional;
import java.math.BigDecimal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inteligente.ponto.response.Response;
import com.inteligente.ponto.dto.FuncionarioDTO;
import com.inteligente.ponto.utils.PasswordUtils;
import com.inteligente.ponto.entities.Funcionario;
import com.inteligente.ponto.services.FuncionarioService;

@RestController
@RequestMapping("/api/funcionario")
@CrossOrigin(origins = "*")
public class FuncionarioController {
	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	public FuncionarioController() {

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDTO>> Atualizar(@PathVariable("id") long id,
			@Valid @RequestBody FuncionarioDTO funcionarioDto, BindingResult result) {
		log.info("Atualizando funcionário: {}", funcionarioDto.toString());
		Response<FuncionarioDTO> response = new Response<FuncionarioDTO>();

		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionário não encontrado."));
		}

		this.atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando funcionário: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.funcionarioService.persistir(funcionario.get());
		response.setData(this.ConverterFuncionarioDto(funcionario.get()));

		return ResponseEntity.ok(response);
	}

	@SuppressWarnings({ "unlikely-arg-type", "null" })
	private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDTO funcionarioDto,
			BindingResult result) {
		funcionario.setNome(funcionarioDto.getNome());

		String funcEmail = funcionarioDto.getEmail();

		if (!funcionario.getLogin().equals(funcEmail)) {
			this.funcionarioService.buscarPorAutorizacaoId(funcionarioDto.getId())
					.ifPresent(func -> result.addError(new ObjectError("email", "Email já existente.")));
			funcionario.getLogin().setEmail(funcEmail);
		}

		funcionario.setQtdHorasAlmoco((Float) null);
		funcionarioDto.getQtdHorasAlmoco()
				.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

		funcionario.setQtdHorasTrabalhoDia((Float) null);
		funcionarioDto.getQtdHorasTrabalhoDia().ifPresent(
				qtdHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalhoDia)));

		funcionario.setValorHora((BigDecimal) null);
		funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

		if (funcionarioDto.getSenha().isPresent()) {
			funcionario.getLogin().setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
		}
	}

	private FuncionarioDTO ConverterFuncionarioDto(Funcionario funcionario) {
		FuncionarioDTO funcionarioDto = new FuncionarioDTO();
		funcionarioDto.setId(funcionario.getId());
		funcionarioDto.setEmail(funcionario.getLogin().getEmail());
		funcionarioDto.setNome(funcionario.getNome());
		funcionarioDto.getQtdHorasAlmoco()
				.ifPresent(qtdHorasAlmoco -> funcionarioDto.setQtdHorasAlmoco(Optional.of(qtdHorasAlmoco)));
		funcionarioDto.getQtdHorasTrabalhoDia().ifPresent(
				qtdHorasTrabalhoDia -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(qtdHorasTrabalhoDia)));
		funcionarioDto.getValorHora()
				.ifPresent(valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));

		return funcionarioDto;
	}
}