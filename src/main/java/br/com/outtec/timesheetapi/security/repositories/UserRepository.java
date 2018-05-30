package br.com.outtec.timesheetapi.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.outtec.timesheetapi.security.domain.User;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
