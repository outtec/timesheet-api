package br.com.outtec.timesheetapi.security.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.security.domain.Usuario;
import br.com.outtec.timesheetapi.security.repositories.UsuarioRepository;
import br.com.outtec.timesheetapi.security.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario> buscarPorEmail(String email) {
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}
}
