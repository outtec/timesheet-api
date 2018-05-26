package br.com.outtec.timesheetapi.security.services;

import java.util.Optional;

import br.com.outtec.timesheetapi.security.domain.Usuario;

public interface UsuarioService {

	/**
	 * Busca e retorna um usuÃ¡rio dado um email.
	 * 
	 * @param email
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorEmail(String email);

}
