package br.com.outtec.timesheetapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.outtec.timesheetapi.domain.Rule;

public interface RuleRepository extends JpaRepository<Rule, Long>{
	
	List<Rule> findByCollaboratorId(Long id);
}
