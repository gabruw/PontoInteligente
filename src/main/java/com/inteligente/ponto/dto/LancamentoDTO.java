package com.inteligente.ponto.dto;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

public class LancamentoDTO {
	private Optional<Long> Id = Optional.empty();

	@NotEmpty(message = "O campo Data não pode ser vazio.")
	private String Data;

	private String Tipo;

	private String Descricao;

	private String Localizacao;

	private long FuncionarioId;

	public LancamentoDTO() {

	}

	public Optional<Long> getId() {
		return Id;
	}

	public void setId(Optional<Long> id) {
		Id = id;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public String getTipo() {
		return Tipo;
	}

	public void setTipo(String tipo) {
		Tipo = tipo;
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}

	public String getLocalizacao() {
		return Localizacao;
	}

	public void setLocalizacao(String localizacao) {
		Localizacao = localizacao;
	}

	public long getFuncionarioId() {
		return FuncionarioId;
	}

	public void setFuncionarioId(long funcionarioId) {
		FuncionarioId = funcionarioId;
	}

	@Override
	public String toString() {
		return "LancamentoDTO [Id=" + Id + ", Data=" + Data + ", Tipo=" + Tipo + ", Descricao=" + Descricao
				+ ", Localizacao=" + Localizacao + ", FuncionarioId=" + FuncionarioId + "]";
	}
}