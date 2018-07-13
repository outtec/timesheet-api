package br.com.outtec.timesheetapi.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.outtec.timesheetapi.security.domain.User;
import br.com.outtec.timesheetapi.enums.Perfil;

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
		return new JwtUser(user.getId(), user.getEmail(), user.getSenha(),null);
	}

	/**
	 * Converte o perfil do usuario para o formato utilizado pelo Spring Security.
	 * 
	 * @param perfilEnum
	 * @return List<GrantedAuthority>
	 */
	private static ArrayList<GrantedAuthority> mapToGrantedAuthorities(Perfil perfil) {
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfil.toString()));
		return authorities;
	}
	
	public static JwtUser authenticated() {
		try {
			return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		}catch(Exception e){
			return null;
		}
		
	}

}
