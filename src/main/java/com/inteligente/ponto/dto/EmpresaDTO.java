package com.inteligente.ponto.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

public class EmpresaDTO {
	private long Id;

	@NotEmpty(message = "O campo Razao Social não pode ser vazio.")
	@Length(min = 5, max = 200, message = "O campo Razao Social deve conter entre 5 a 200 caracteres.")
	private String RazaoSocial;

	@NotEmpty(message = "O campo CNPJ não pode ser vazio.")
	@CNPJ(message = "CNPJ inválido.")
	private String Cnpj;

	public EmpresaDTO() {

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

	@Override
	public String toString() {
		return "CadastroEmpresaDTO [Id=" + Id + ", RazaoSocial=" + RazaoSocial + ", Cnpj=" + Cnpj + "]";
	}
}