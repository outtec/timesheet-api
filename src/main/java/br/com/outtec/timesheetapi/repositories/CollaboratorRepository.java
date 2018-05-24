package br.com.outtec.timesheetapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.outtec.timesheetapi.domain.Collaborator;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long>{

		Collaborator findByName(String name);	
}
