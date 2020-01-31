package com.inteligente.ponto.services;

import java.util.Optional;
import com.inteligente.ponto.entities.Empresa;

public interface EmpresaService {
	Optional<Empresa> buscarPorCnpj(String cnpj);

	Empresa persistir(Empresa empresa);
}