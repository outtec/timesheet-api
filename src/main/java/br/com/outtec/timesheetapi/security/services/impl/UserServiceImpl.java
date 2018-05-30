package br.com.outtec.timesheetapi.security.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.security.domain.User;
import br.com.outtec.timesheetapi.security.repositories.UserRepository;
import br.com.outtec.timesheetapi.security.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository usuarioRepository;
	
	public Optional<User> buscarPorEmail(String email) {
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}
}
