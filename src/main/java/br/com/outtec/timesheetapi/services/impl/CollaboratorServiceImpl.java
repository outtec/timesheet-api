package br.com.outtec.timesheetapi.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.repositories.CollaboratorRepository;
import br.com.outtec.timesheetapi.services.CollaboratorService;


@Service
public class CollaboratorServiceImpl  implements CollaboratorService{
	
	private static final Logger log = LoggerFactory.getLogger(CollaboratorServiceImpl.class);
	@Autowired
	private CollaboratorRepository collaboratorRepository;
	
	@Override
	public Collaborator save(Collaborator collaborator) {
		log.info("Salvando Colaborador");
		return this.collaboratorRepository.save(collaborator);
	}

	@Override
	public Optional<Collaborator> findByID(Long id) {
		return this.collaboratorRepository.findById(id);
	}

	@Override
	public List<Collaborator> returnCollaborators() {
		return this.collaboratorRepository.findAll();
	}

	@Override
	public void delete(Long id) {
	this.collaboratorRepository.deleteById(id);	
	}
	
}
