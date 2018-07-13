package br.com.outtec.timesheetapi.security.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.security.JwtUserFactory;
import br.com.outtec.timesheetapi.security.domain.User;
import br.com.outtec.timesheetapi.security.services.UserService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> User = userService.buscarPorEmail(username);

		if (User.isPresent()) {
			return JwtUserFactory.create(User.get());
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}
	
	
}
