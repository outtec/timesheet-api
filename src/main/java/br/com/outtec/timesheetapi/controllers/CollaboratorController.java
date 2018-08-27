package br.com.outtec.timesheetapi.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.dtos.CollaboratorDto;
import br.com.outtec.timesheetapi.services.CollaboratorService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/collaborators")
@CrossOrigin(origins = "*")
public class CollaboratorController {

	private static final Logger log = LoggerFactory.getLogger(CollaboratorController.class);

	public CollaboratorController() {}

	@Autowired
	private CollaboratorService service;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Collaborator> find(@PathVariable Long id) throws ObjectNotFoundException {
		Collaborator obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Collaborator> find(@RequestParam(value="value") String email) throws ObjectNotFoundException {
		Collaborator obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CollaboratorDto objDto) {
		Collaborator obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CollaboratorDto objDto, @PathVariable Long id) throws ObjectNotFoundException {
		Collaborator obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) throws ObjectNotFoundException {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CollaboratorDto>> findAll() {
		List<Collaborator> list = service.findAll();
		List<CollaboratorDto> listDto = list.stream().map(obj -> new CollaboratorDto(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CollaboratorDto>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Collaborator> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CollaboratorDto> listDto = list.map(obj -> new CollaboratorDto(obj));  
		return ResponseEntity.ok().body(listDto);
	}	
	

}
