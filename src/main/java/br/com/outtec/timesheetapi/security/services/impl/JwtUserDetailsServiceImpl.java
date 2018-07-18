package br.com.outtec.timesheetapi.security.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.repositories.CollaboratorRepository;
import br.com.outtec.timesheetapi.security.JwtUser;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CollaboratorRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Collaborator user = repo.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), user.getPerfis());
	}
		
}