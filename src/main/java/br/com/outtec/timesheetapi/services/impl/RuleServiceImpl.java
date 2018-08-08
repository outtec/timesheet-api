package br.com.outtec.timesheetapi.services.impl;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.domain.Rule;
import br.com.outtec.timesheetapi.dtos.RuleDto;
import br.com.outtec.timesheetapi.repositories.RuleRepository;
import br.com.outtec.timesheetapi.services.RuleService;

@Service
public class RuleServiceImpl implements RuleService{
	
	private static final Logger log = LoggerFactory.getLogger(RuleServiceImpl.class);

	@Autowired
	private RuleRepository repo;

	public Rule save(Rule obj) { 
		log.info("Persistindo regra de calculo: {}", obj);
		return this.repo.save(obj); 
	}
	
	@Override
	public List<Rule> findByCollaboratorId(Long id) {
		log.info("Buscando regra por ID do colaborador: {}", id);
		return this.repo.findByCollaboratorId(id);
	}
	
	public List<Rule> findAll(){
		log.info("Retornando regras");
		return this.repo.findAll();
	}

	@Override
	public Rule fromDTO(@Valid RuleDto objDto) throws ParseException {

		return new Rule(objDto.getId(),objDto.getInitialHour(),objDto.getFinalHour(),objDto.getRule(),objDto.getValue());
	}

}
