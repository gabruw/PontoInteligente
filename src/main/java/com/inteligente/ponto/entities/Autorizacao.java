package com.inteligente.ponto.entities;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "autorizacao")
public class Autorizacao implements Serializable {
	private static final long serialVersionUID = 117456258955L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;
	
	@Column(name = "email", nullable = false)
	private String Email;

	@Column(name = "senha", nullable = false)
	private String Senha;

	public Autorizacao() {
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
}