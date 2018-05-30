package br.com.outtec.timesheetapi.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.outtec.timesheetapi.security.domain.User;
import br.com.outtec.timesheetapi.enums.PerfilEnum;

public class JwtUserFactory {

	private JwtUserFactory() {
	}
	/**
	 * Converte e gera um JwtUser com base nos dados de um Colaborador.
	 * 
	 * @param funcionario
	 * @return JwtUser
	 */
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getSenha(),
				mapToGrantedAuthorities(user.getPerfil()));
	}

	/**
	 * Converte o perfil do usuario para o formato utilizado pelo Spring Security.
	 * 
	 * @param perfilEnum
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfilEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
		return authorities;
	}

}
