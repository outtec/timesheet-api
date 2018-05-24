package br.com.outtec.timesheetapi.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.repositories.CollaboratorRepository;
import br.com.outtec.timesheetapi.services.CollaboratorService;
import br.com.outtec.utils.Response;

public class CollaboratorServiceImpl  implements CollaboratorService{
	
	private static final Logger log = LoggerFactory.getLogger(CollaboratorServiceImpl.class);
	@Autowired
	private CollaboratorRepository collaboratorRepository;
	
	@Override
	public Response<Collaborator> save(Collaborator Collaborator) {
		Response<Collaborator> response = new Response<Collaborator>();
		this.collaboratorRepository.save(Collaborator);
		response.getErrors().add("Colaborador salvo com sucesso");
		return response;
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
