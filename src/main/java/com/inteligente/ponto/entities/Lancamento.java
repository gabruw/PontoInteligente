package com.inteligente.ponto.entities;

import java.util.Date;
import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.ManyToOne;
import javax.persistence.TemporalType;

import com.inteligente.ponto.enums.TipoEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.PreUpdate;
import javax.persistence.PrePersist;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "lancamento")
public class Lancamento implements Serializable {
	private static final long serialVersionUID = 445257956256L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", nullable = false)
	private Date Data;
	
	@Column(name = "descricao", nullable = true)
	private String Descricao;
	
	@Column(name = "localizacao", nullable = true)
	private String Localizacao;
	
	@Column(name = "data_criacao", nullable = false)
	private Date DataCriacao;

	@Column(name = "data_atualizacao", nullable = false)
	private Date DataAtualizacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	private TipoEnum Tipo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Funcionario Funcionario;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public Date getData() {
		return Data;
	}

	public void setData(Date data) {
		Data = data;
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

	public Date getDataCriacao() {
		return DataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		DataCriacao = dataCriacao;
	}

	public Date getDataAtualizacao() {
		return DataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		DataAtualizacao = dataAtualizacao;
	}

	public TipoEnum getTipo() {
		return Tipo;
	}

	public void setTipo(TipoEnum tipo) {
		Tipo = tipo;
	}

	public Funcionario getFuncionario() {
		return Funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		Funcionario = funcionario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@PreUpdate
	public void preUpdate() {
		DataAtualizacao = new Date();
	}

	@PrePersist
	public void prePersist() {
		final Date atual = new Date();
		DataCriacao = atual;
		DataAtualizacao = atual;
	}

	@Override
	public String toString() {
		return "Lancamento [Id=" + Id + ", Data=" + Data + ", Descricao=" + Descricao + ", Localizacao=" + Localizacao
				+ ", DataCriacao=" + DataCriacao + ", DataAtualizacao=" + DataAtualizacao + ", Tipo=" + Tipo
				+ ", Funcionario=" + Funcionario + "]";
	}
}
