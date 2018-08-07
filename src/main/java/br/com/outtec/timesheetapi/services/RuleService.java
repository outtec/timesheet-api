package br.com.outtec.timesheetapi.services;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import br.com.outtec.timesheetapi.domain.Rule;
import br.com.outtec.timesheetapi.dtos.RuleDto;

public interface RuleService {

	List<Rule> findByCollaboratorId(Long id);

	Rule save(Rule obj);

	Rule fromDTO(@Valid RuleDto objDto) throws ParseException;
	
	
}
