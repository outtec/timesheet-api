package br.com.outtec.timesheetapi.security.services;

import java.util.Optional;

import br.com.outtec.timesheetapi.security.domain.User;

public interface UserService {

	/**
	 * Busca e retorna um usuÃ¡rio dado um email.
	 * 
	 * @param email
	 * @return Optional<User>
	 */
	Optional<User> buscarPorEmail(String email);

}
