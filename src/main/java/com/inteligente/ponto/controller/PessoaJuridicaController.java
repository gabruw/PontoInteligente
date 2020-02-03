package com.inteligente.ponto.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.inteligente.ponto.enums.PerfilEnum;
import com.inteligente.ponto.entities.Empresa;
import com.inteligente.ponto.response.Response;
import com.inteligente.ponto.utils.PasswordUtils;
import com.inteligente.ponto.entities.Funcionario;
import com.inteligente.ponto.entities.Autorizacao;
import com.inteligente.ponto.services.EmpresaService;
import com.inteligente.ponto.services.AutorizacaoService;
import com.inteligente.ponto.services.FuncionarioService;
import com.inteligente.ponto.dto.CadastroPessoaJuridicaDTO;

@RestController
@RequestMapping("/api/pessoa-juridica")
@CrossOrigin(origins = "*")
public class PessoaJuridicaController {
	private static final Logger log = LoggerFactory.getLogger(PessoaJuridicaController.class);

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private AutorizacaoService autorizacaoService;

	public PessoaJuridicaController() {

	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Response<CadastroPessoaJuridicaDTO>> Cadastrar(
			@Valid @RequestBody CadastroPessoaJuridicaDTO cadastroPessoaJuridica, BindingResult result) {
		log.info("Cadastrando uma Pessoa Jurídica: {}", cadastroPessoaJuridica.toString());
		Response<CadastroPessoaJuridicaDTO> response = new Response<CadastroPessoaJuridicaDTO>();

		this.ValidarDadosExistentes(cadastroPessoaJuridica, result);
		Empresa empresa = this.ConverterDtoParaEmpresa(cadastroPessoaJuridica);
		Autorizacao autorizacao = this.ConverterDtoParaAutorizacao(cadastroPessoaJuridica);
		Funcionario funcionario = this.ConverterDtoParaFuncionario(cadastroPessoaJuridica);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro Pessoa Jurídica: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.empresaService.persistir(empresa);
		this.autorizacaoService.persistir(autorizacao);

		funcionario.setEmpresa(empresa);
		funcionario.setLogin(autorizacao);
		this.funcionarioService.persistir(funcionario);

		response.setData(this.ConverterCadastroPessoaJuridicaDTO(funcionario));
		return ResponseEntity.ok(response);
	}

	private void ValidarDadosExistentes(CadastroPessoaJuridicaDTO cadastroPessoaJuridica, BindingResult result) {
		this.empresaService.buscarPorCnpj(cadastroPessoaJuridica.getCnpj())
				.ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente.")));

		this.funcionarioService.buscarPorCpf(cadastroPessoaJuridica.getCpf())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "Funcionário já existente.")));

		this.autorizacaoService.buscarPorEmail(cadastroPessoaJuridica.getEmail())
				.ifPresent(aut -> result.addError(new ObjectError("autorizacao", "Email já existente.")));
	}

	private Empresa ConverterDtoParaEmpresa(CadastroPessoaJuridicaDTO cadastroPessoaJuridica) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPessoaJuridica.getCnpj());
		empresa.setRazaoSocial(cadastroPessoaJuridica.getRazaoSocial());

		return empresa;
	}

	private Autorizacao ConverterDtoParaAutorizacao(CadastroPessoaJuridicaDTO cadastroPessoaJuridica) {
		Autorizacao autorizacao = new Autorizacao();
		autorizacao.setEmail(cadastroPessoaJuridica.getEmail());
		autorizacao.setSenha(PasswordUtils.gerarBCrypt(cadastroPessoaJuridica.getSenha()));

		return autorizacao;
	}

	private Funcionario ConverterDtoParaFuncionario(CadastroPessoaJuridicaDTO cadastroPessoaJuridica) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(cadastroPessoaJuridica.getNome());
		funcionario.setCpf(cadastroPessoaJuridica.getCpf());
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);

		return funcionario;
	}

	private CadastroPessoaJuridicaDTO ConverterCadastroPessoaJuridicaDTO(Funcionario funcionario) {
		CadastroPessoaJuridicaDTO cadastroPessoaJuridica = new CadastroPessoaJuridicaDTO();
		cadastroPessoaJuridica.setId(funcionario.getId());
		cadastroPessoaJuridica.setNome(funcionario.getNome());
		cadastroPessoaJuridica.setEmail(funcionario.getLogin().getEmail());
		cadastroPessoaJuridica.setCpf(funcionario.getCpf());
		cadastroPessoaJuridica.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastroPessoaJuridica.setCnpj(funcionario.getEmpresa().getCnpj());

		return cadastroPessoaJuridica;
	}
}