package br.com.outtec.timesheetapi.controllers;

import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.dtos.CollaboratorDto;
import br.com.outtec.timesheetapi.enums.PerfilEnum;
import br.com.outtec.timesheetapi.services.CollaboratorService;
import br.com.outtec.utils.PasswordUtils;
import br.com.outtec.utils.Response;

@RestController
@RequestMapping("api/v1/collaborators")
@CrossOrigin(origins = "*")
public class CollaboratorController {

	private static final Logger log = LoggerFactory.getLogger(CollaboratorController.class);

	public CollaboratorController() {}

	@Autowired
	private CollaboratorService collaboratorService;


	@PostMapping("")
	public ResponseEntity<Response<CollaboratorDto>> save(@Valid @RequestBody CollaboratorDto collaboratorDto, 
			BindingResult result) throws ParseException{
		log.info("Adicionando Colaborador {}", collaboratorDto.toString());
		Response<CollaboratorDto> response = new Response<CollaboratorDto>();
		Collaborator obj = this.convertDtoToCollaborator(collaboratorDto, result);
		//TRATAERRO 
		if(result.hasErrors()) { 
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage())); 
			return ResponseEntity.badRequest().body(response); 
		} 
		obj = this.collaboratorService.save(obj);
		response.setData(this.converteCollaboratorToDto(obj));
		return ResponseEntity.ok(response);
	}

	private Collaborator convertDtoToCollaborator(@Valid CollaboratorDto collaboratorDto, BindingResult result) {
		Collaborator collaborator = new Collaborator();
		collaborator.setName(collaboratorDto.getName());
		collaborator.setPassword(PasswordUtils.getBCrypt(collaboratorDto.getPassword()));
		collaborator.setPerfil(PerfilEnum.ROLE_USER);
		return collaborator;
	}
	
	private CollaboratorDto converteCollaboratorToDto(Collaborator obj) {
		CollaboratorDto collaboratorDto = new CollaboratorDto();
		collaboratorDto.setId(Optional.of(obj.getId()));
		collaboratorDto.setName(obj.getName());
		collaboratorDto.setPerfil(obj.getPerfil());
		return collaboratorDto;
	}
}
