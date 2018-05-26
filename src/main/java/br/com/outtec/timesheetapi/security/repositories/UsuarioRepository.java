package br.com.outtec.timesheetapi.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.outtec.timesheetapi.security.domain.Usuario;

@Transactional(readOnly = true)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);
}
