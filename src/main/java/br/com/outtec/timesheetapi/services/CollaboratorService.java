package br.com.outtec.timesheetapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.utils.Response;

public interface CollaboratorService {
	/**
	 * Save a new hours period
	 * @param collaborator
	 * @return Collaborator
	 */
	Response<Collaborator> save(Collaborator Collaborator);
	
	/**
	 * Return a Collaborator by ID
	 * @param Collaborator
	 * @return Collaborator
	 */
	Optional<Collaborator> findByID(Long id);
	
	/**
	 * List all Collaborators entrys
	 * @return List<Collaborator>
	 */
	List<Collaborator> returnCollaborators();
	
	/**
	 * Delete a Collaborator by ID
	 * @param id
	 */
	void delete(Long id);
}
