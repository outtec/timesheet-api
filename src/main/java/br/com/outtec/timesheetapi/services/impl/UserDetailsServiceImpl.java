package br.com.outtec.timesheetapi.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.repositories.CollaboratorRepository;
import br.com.outtec.timesheetapi.security.JwtUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CollaboratorRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Collaborator collaborator = repo.findByEmail(email);
		if (collaborator == null) {
			throw new UsernameNotFoundException(email);
		}

		return new JwtUser(collaborator.getId(), collaborator.getEmail(), collaborator.getPassword(), collaborator.getPerfis());
	}
		
}