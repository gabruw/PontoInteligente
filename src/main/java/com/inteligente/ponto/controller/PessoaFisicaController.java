package com.inteligente.ponto.controller;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inteligente.ponto.entities.Autorizacao;
import com.inteligente.ponto.entities.Empresa;
import com.inteligente.ponto.response.Response;
import com.inteligente.ponto.entities.Funcionario;
import com.inteligente.ponto.enums.PerfilEnum;
import com.inteligente.ponto.services.EmpresaService;
import com.inteligente.ponto.services.AutorizacaoService;
import com.inteligente.ponto.services.FuncionarioService;
import com.inteligente.ponto.dto.CadastroPessoaFisicaDTO;

@RestController
@RequestMapping("/api/pessoa-fisica")
@CrossOrigin(origins = "*")
public class PessoaFisicaController {
	private static final Logger log = LoggerFactory.getLogger(PessoaFisicaController.class);

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private AutorizacaoService autorizacaoService;

	public PessoaFisicaController() {

	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Response<CadastroPessoaFisicaDTO>> Cadastrar(
			@Valid @RequestBody CadastroPessoaFisicaDTO cadastroPessoaFisica, BindingResult result) {
		log.info("Cadastrando uma Pessoa Física: {}", cadastroPessoaFisica.toString());
		Response<CadastroPessoaFisicaDTO> response = new Response<CadastroPessoaFisicaDTO>();

		this.ValidarDadosExistentes(cadastroPessoaFisica, result);
		Funcionario funcionario = this.ConverterDtoParaFuncionario(cadastroPessoaFisica, result);
		Autorizacao autorizacao = this.ConverterDtoParaAutorizacao(cadastroPessoaFisica);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro de Pessoa Física: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPessoaFisica.getCnpj());
		empresa.ifPresent(emp -> funcionario.setEmpresa(emp));

		funcionario.setLogin(autorizacao);
		this.funcionarioService.persistir(funcionario);

		response.setData(this.ConverterCadastroPessoaFisicaDTO(funcionario));
		return ResponseEntity.ok(response);
	}

	private void ValidarDadosExistentes(CadastroPessoaFisicaDTO cadastroPessoaFisica, BindingResult result) {
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPessoaFisica.getCnpj());
		if (!empresa.isPresent()) {
			result.addError(new ObjectError("funcionario", "CPF já existente"));
		}

		this.funcionarioService.buscarPorCpf(cadastroPessoaFisica.getCpf())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já existente.")));

		this.autorizacaoService.buscarPorEmail(cadastroPessoaFisica.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("autorizacao", "Email já existente.")));
	};

	private Funcionario ConverterDtoParaFuncionario(CadastroPessoaFisicaDTO cadastroPessoaFisica,
			BindingResult result) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(cadastroPessoaFisica.getNome());
		funcionario.setCpf(cadastroPessoaFisica.getCpf());
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);

		cadastroPessoaFisica.getQtdHorasAlmoco()
				.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		cadastroPessoaFisica.getQtdHorasTrabalhoDia().ifPresent(
				qtdHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalhoDia)));
		cadastroPessoaFisica.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

		return funcionario;

	}

	private Autorizacao ConverterDtoParaAutorizacao(CadastroPessoaFisicaDTO cadastroPessoaFisica) {
		Autorizacao autorizacao = new Autorizacao();
		autorizacao.setEmail(cadastroPessoaFisica.getEmail());
		autorizacao.setSenha(cadastroPessoaFisica.getSenha());

		return autorizacao;
	}

	private CadastroPessoaFisicaDTO ConverterCadastroPessoaFisicaDTO(Funcionario funcionario) {
		CadastroPessoaFisicaDTO cadastroPessoaFisica = new CadastroPessoaFisicaDTO();
		cadastroPessoaFisica.setId(funcionario.getId());
		cadastroPessoaFisica.setNome(funcionario.getNome());
		cadastroPessoaFisica.setEmail(funcionario.getLogin().getEmail());
		cadastroPessoaFisica.setCpf(funcionario.getCpf());
		cadastroPessoaFisica.setCnpj(funcionario.getEmpresa().getCnpj());

		funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHorasAlmoco -> cadastroPessoaFisica
				.setQtdHorasAlmoco((Optional.of(Float.toString(qtdHorasAlmoco)))));
		funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(qtdHorasTrabalho -> cadastroPessoaFisica
				.setQtdHorasTrabalhoDia((Optional.of(Float.toString(qtdHorasTrabalho)))));
		funcionario.getValorHoraOpt()
				.ifPresent(valorHora -> cadastroPessoaFisica.setValorHora(Optional.of(valorHora.toString())));

		return cadastroPessoaFisica;
	}
}