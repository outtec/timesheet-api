package br.com.outtec.timesheetapi.controllers;

import java.net.URI;
import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.outtec.timesheetapi.domain.Rule;
import br.com.outtec.timesheetapi.dtos.RuleDto;
import br.com.outtec.timesheetapi.services.RuleService;

@RestController
@RequestMapping("/rules")
@CrossOrigin(origins = "*")
public class RuleController {
	
	
	@Autowired
	RuleService service;
	
	public RuleController(){}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody RuleDto objDto) throws ParseException {
		Rule obj = service.fromDTO(objDto);
		obj = service.save(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
}
