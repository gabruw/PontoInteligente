package com.inteligente.ponto.dto;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

public class CadastroPessoaFisicaDTO {
	private long Id;

	@NotEmpty(message = "O campo Nome não pode estar vazio.")
	@Length(min = 3, max = 200, message = "O campo Nome deve conter entre 3 a 200 caracteres.")
	private String Nome;

	@NotEmpty(message = "O campo Email não pode ser vazio.")
	@Length(min = 5, max = 200, message = "O campo Email deve conter entre 5 a 200 caracteres.")
	@Email(message = "Email inválido.")
	private String Email;

	@NotEmpty(message = "O campo Senha não pode ser vazio.")
	private String Senha;

	@NotEmpty(message = "O campo CPF não pode ser vazio.")
	@CPF(message = "CPF inválido.")
	private String Cpf;

	@NotEmpty(message = "O campo CNPJ não pode ser vazio.")
	@CNPJ(message = "CNPJ inválido.")
	private String Cnpj;

	private Optional<String> ValorHora = Optional.empty();

	private Optional<String> QtdHorasTrabalhoDia = Optional.empty();

	private Optional<String> QtdHorasAlmoco = Optional.empty();

	public CadastroPessoaFisicaDTO() {

	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getSenha() {
		return Senha;
	}

	public void setSenha(String senha) {
		Senha = senha;
	}

	public String getCpf() {
		return Cpf;
	}

	public void setCpf(String cpf) {
		Cpf = cpf;
	}

	public String getCnpj() {
		return Cnpj;
	}

	public void setCnpj(String cnpj) {
		Cnpj = cnpj;
	}

	public Optional<String> getValorHora() {
		return ValorHora;
	}

	public void setValorHora(Optional<String> valorHora) {
		ValorHora = valorHora;
	}

	public Optional<String> getQtdHorasTrabalhoDia() {
		return QtdHorasTrabalhoDia;
	}

	public void setQtdHorasTrabalhoDia(Optional<String> qtdHorasTrabalhoDia) {
		QtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}

	public Optional<String> getQtdHorasAlmoco() {
		return QtdHorasAlmoco;
	}

	public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
		QtdHorasAlmoco = qtdHorasAlmoco;
	}

	@Override
	public String toString() {
		return "CadastroPessoaFisicaDTO [Id=" + Id + ", Nome=" + Nome + ", Email=" + Email + ", Senha=" + Senha
				+ ", ValorHora=" + ValorHora + ", QtdHorasTrabalhoDia=" + QtdHorasTrabalhoDia + ", QtdHorasAlmoco="
				+ QtdHorasAlmoco + ", Cpf=" + Cpf + ", Cnpj=" + Cnpj + "]";
	}
}