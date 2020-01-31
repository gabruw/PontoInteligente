package com.inteligente.ponto.entities;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.PreUpdate;
import javax.persistence.PrePersist;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {
	private static final long serialVersionUID = 159756987401L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;

	@Column(name = "razao_social", nullable = false)
	private String RazaoSocial;

	@Column(name = "cnpj", nullable = false)
	private String Cnpj;

	@Column(name = "data_criacao", nullable = false)
	private Date DataCriacao;

	@Column(name = "data_atualizacao", nullable = false)
	private Date DataAtualizacao;

	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Funcionario> Funcionarios;

	public Empresa() {

	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getRazaoSocial() {
		return RazaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		RazaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return Cnpj;
	}

	public void setCnpj(String cnpj) {
		Cnpj = cnpj;
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

	public List<Funcionario> getFuncionarios() {
		return Funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		Funcionarios = funcionarios;
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
		return "Empresa [Id=" + Id + ", RazaoSocial=" + RazaoSocial + ", Cnpj=" + Cnpj + ", DataCriacao=" + DataCriacao
				+ ", DataAtualizacao=" + DataAtualizacao + ", Funcionarios=" + Funcionarios + "]";
	}
}