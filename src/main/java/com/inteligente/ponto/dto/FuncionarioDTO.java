package com.inteligente.ponto.dto;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class FuncionarioDTO {
	private long Id;

	@NotEmpty(message = "O campo Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "O campo Nome deve conter entre 3 a 200 caracteres.")
	private String Nome;

	@NotEmpty(message = "O campo Email não pode ser vazio.")
	@Length(min = 5, max = 200, message = "O campo Email deve conter entre 5 a 200 caracteres.")
	@Email(message = "Email inválido.")
	private String Email;

	private Optional<String> Senha = Optional.empty();

	private Optional<String> ValorHora = Optional.empty();

	private Optional<String> QtdHorasTrabalhoDia = Optional.empty();

	private Optional<String> QtdHorasAlmoco = Optional.empty();

	public FuncionarioDTO() {

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

	public Optional<String> getSenha() {
		return Senha;
	}

	public void setSenha(Optional<String> senha) {
		Senha = senha;
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
		return "FuncionarioDTO [Id=" + Id + ", Nome=" + Nome + ", Email=" + Email + ", Senha=" + Senha + ", ValorHora="
				+ ValorHora + ", QtdHorasTrabalhoDia=" + QtdHorasTrabalhoDia + ", QtdHorasAlmoco=" + QtdHorasAlmoco
				+ "]";
	}
}