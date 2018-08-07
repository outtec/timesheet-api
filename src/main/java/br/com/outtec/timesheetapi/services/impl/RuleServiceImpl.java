package br.com.outtec.timesheetapi.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm"); //"dd/MM/yyyy HH:mm"

	@Autowired
	private RuleRepository repo;

	public Rule save(Rule obj) { 
		log.info("Persistindo regra de calculo: {}", obj);
		return this.repo.save(obj); 
	}
	
	@Override
	public List<Rule> findByCollaboratorId(Long id) {
		return this.repo.findByCollaboratorId(id);
	}
	
	public List<Rule> findAll(){
		return this.repo.findAll();
	}

	@Override
	public Rule fromDTO(@Valid RuleDto objDto) throws ParseException {
		Date initialTime = simpleDateFormat.parse(objDto.getInitialHour());
		Date finalTime = simpleDateFormat.parse(objDto.getFinalHour());
		
		
		return new Rule(objDto.getId(),initialTime,finalTime,objDto.getRule(),objDto.getValue());
	}

}
