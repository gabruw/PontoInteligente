package com.inteligente.ponto.entities;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.inteligente.ponto.enums.PerfilEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.PreUpdate;
import javax.persistence.PrePersist;
import javax.persistence.Enumerated;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {
	private static final long serialVersionUID = 889757182555L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;

	@Column(name = "nome", nullable = false)
	private String Nome;

	@Column(name = "cpf", nullable = false)
	private String Cpf;

	@Column(name = "valor_hora", nullable = true)
	private BigDecimal ValorHora;

	@Column(name = "qtd_horas_trabalho_dia", nullable = true)
	private float QtdHorasTrabalhoDia;

	@Column(name = "qtd_horas_almoco", nullable = true)
	private float QtdHorasAlmoco;

	@Column(name = "data_criacao", nullable = false)
	private Date DataCriacao;

	@Column(name = "data_atualizacao", nullable = false)
	private Date DataAtualizacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "perfil", nullable = false)
	private PerfilEnum Perfil;

	@ManyToOne(fetch = FetchType.EAGER)
	private Autorizacao Login;

	@ManyToOne(fetch = FetchType.EAGER)
	private Empresa Empresa;

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Lancamento> Lancamentos;

	public Funcionario() {

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

	public String getCpf() {
		return Cpf;
	}

	public void setCpf(String cpf) {
		Cpf = cpf;
	}

	public BigDecimal getValorHora() {
		return ValorHora;
	}

	@Transient
	public Optional<BigDecimal> getValorHoraOpt() {
		return Optional.ofNullable(ValorHora);
	}

	public void setValorHora(BigDecimal valorHora) {
		ValorHora = valorHora;
	}

	public float getQtdHorasTrabalhoDia() {
		return QtdHorasTrabalhoDia;
	}

	@Transient
	public Optional<Float> getQtdHorasTrabalhoDiaOpt() {
		return Optional.ofNullable(QtdHorasTrabalhoDia);
	}

	public void setQtdHorasTrabalhoDia(float qtdHorasTrabalhoDia) {
		QtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}

	public float getQtdHorasAlmoco() {
		return QtdHorasAlmoco;
	}

	@Transient
	public Optional<Float> getQtdHorasAlmocoOpt() {
		return Optional.ofNullable(QtdHorasAlmoco);
	}

	public void setQtdHorasAlmoco(float qtdHorasAlmoco) {
		QtdHorasAlmoco = qtdHorasAlmoco;
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

	public PerfilEnum getPerfil() {
		return Perfil;
	}

	public void setPerfil(PerfilEnum perfil) {
		Perfil = perfil;
	}

	public Autorizacao getLogin() {
		return Login;
	}

	public void setLogin(Autorizacao login) {
		Login = login;
	}

	public Empresa getEmpresa() {
		return Empresa;
	}

	public void setEmpresa(Empresa empresa) {
		Empresa = empresa;
	}

	public List<Lancamento> getLancamentos() {
		return Lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		Lancamentos = lancamentos;
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
		return "Funcionario [Id=" + Id + ", Nome=" + Nome + ", Cpf=" + Cpf + ", ValorHora=" + ValorHora
				+ ", QtdHorasTrabalhoDia=" + QtdHorasTrabalhoDia + ", QtdHorasAlmoco=" + QtdHorasAlmoco
				+ ", DataCriacao=" + DataCriacao + ", DataAtualizacao=" + DataAtualizacao + ", Perfil=" + Perfil
				+ ", Login=" + Login + ", Empresa=" + Empresa + ", Lancamentos=" + Lancamentos + "]";
	}
}