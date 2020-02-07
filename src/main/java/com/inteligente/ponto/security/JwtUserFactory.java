package com.inteligente.ponto.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.inteligente.ponto.entities.Funcionario;
import com.inteligente.ponto.enums.PerfilEnum;

public class JwtUserFactory {
	private JwtUserFactory() {

	}

	public static JwtUser create(Funcionario funcionario) {
		return new JwtUser(funcionario.getId(), funcionario.getLogin().getEmail(), funcionario.getLogin().getSenha(),
				mapToGrantedAuthorities(funcionario.getPerfil()));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfilEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
		return authorities;
	}
}