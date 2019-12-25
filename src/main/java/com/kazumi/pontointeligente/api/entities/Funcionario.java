package com.kazumi.pontointeligente.api.entities;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.kazumi.pontointeligente.api.enums.PerfilEnum;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {
    private static final long serialVersionUID = -9978956586564594L;

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private BigDecimal valorHora;
    private Float qtdHorasTrabalhoDia;
    private Float qtdHorasAlmoco;
    private PerfilEnum perfil;
    private Date dataCriacao;
    private Date dataAtualizacao;
    private Empresa empresa;
    private List<Lancamento> lancamentos;

    public Funcionario() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "nome", nullable = false)
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "senha", nullable = false)
    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Column(name = "cpf", nullable = false)
    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Column(name = "valor_hora", nullable = true)
    public BigDecimal getValorHora() {
        return this.valorHora;
    }

    @Transient
    public Optional<BigDecimal> getValorHoraOpt() {
        return Optional.ofNullable(valorHora);
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    @Column(name = "qtd_horas_trabalho_dia", nullable = true)
    public Float getQtdHorasTrabalhoDia() {
        return this.qtdHorasTrabalhoDia;
    }

    @Transient
    public Optional<Float> getQtdHorasTrabalhoDiaOpt() {
        return Optional.ofNullable(qtdHorasTrabalhoDia);
    }

    public void setQtdHorasTrabalhoDia(Float qtdHorasTrabalhoDia) {
        this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
    }

    @Column(name = "qtd_horas_almoco", nullable = true)
    public Float getQtdHorasAlmoco() {
        return this.qtdHorasAlmoco;
    }

    @Transient
    public Optional<Float> getQtdHorasAlmocoOpt() {
        return Optional.ofNullable(qtdHorasAlmoco);
    }

    public void setQtdHorasAlmoco(Float qtdHorasAlmoco) {
        this.qtdHorasAlmoco = qtdHorasAlmoco;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    public PerfilEnum getPerfil() {
        return this.perfil;
    }

    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }

    @Column(name = "data_criacao", nullable = false)
    public Date getDataCriacao() {
        return this.dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Column(name = "data_atualizacao", nullable = false)
    public Date getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @OneToMany(mappedBy = "funcioanrio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Lancamento> getLancamentos() {
        return this.lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", nome='" + getNome() + "'" + ", email='" + getEmail() + "'"
                + ", senha='" + getSenha() + "'" + ", cpf='" + getCpf() + "'" + ", valorHora='" + getValorHora() + "'"
                + ", qtdHorasTrabalhoDia='" + getQtdHorasTrabalhoDia() + "'" + ", qtdHorasAlmoco='"
                + getQtdHorasAlmoco() + "'" + ", perfil='" + getPerfil() + "'" + ", dataCriacao='" + getDataCriacao()
                + "'" + ", dataAtualizacao='" + getDataAtualizacao() + "'" + ", empresa='" + getEmpresa() + "'"
                + ", lancamentos='" + getLancamentos() + "'" + "}";
    }

}
