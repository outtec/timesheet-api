package br.com.outtec.timesheetapi.services;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.dtos.CollaboratorDto;
import javassist.tools.rmi.ObjectNotFoundException;

public interface CollaboratorService {


	URI uploadProfilePicture(MultipartFile file);

	Collaborator findByEmail(String email) throws ObjectNotFoundException;

	Collaborator insert(Collaborator obj);

	Collaborator fromDTO(@Valid CollaboratorDto objDto);

	Collaborator update(Collaborator obj) throws ObjectNotFoundException;

	void delete(Long id) throws ObjectNotFoundException;

	List<Collaborator> findAll();

	Page<Collaborator> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);

	Collaborator find(Long id) throws ObjectNotFoundException;
}
