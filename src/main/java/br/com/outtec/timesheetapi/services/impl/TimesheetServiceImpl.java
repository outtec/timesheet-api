package br.com.outtec.timesheetapi.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.repositories.TimesheetRepository;
import br.com.outtec.timesheetapi.services.TimesheetService;
import br.com.outtec.utils.Response;


@Service
public class TimesheetServiceImpl implements TimesheetService{

	private static final Logger log = LoggerFactory.getLogger(TimesheetServiceImpl.class);

	@Autowired
	private TimesheetRepository timesheetRepository;

	public Response<Timesheet> save(Timesheet timesheetRequest) {
		Response<Timesheet> response = new Response<Timesheet>();
		log.info("Persistindo Timesheet: {}", timesheetRequest);
		
		List<Timesheet> lancamentos = returnTimesheets();	
		for (int i = 0; i < lancamentos.size(); i++) {
			Timesheet timesheet = lancamentos.get(i);	
			log.info("Lista: {}", timesheet.getCollaborator());	
		}
		if(findTimesheetByCollaborator(timesheetRequest).isPresent()){
			response.getErrors().add("Já existe um período cadastrado com a Data de Entrada, Saída e Horários que foram fornecidos.");
			return response;
		}else {
			this.timesheetRepository.save(timesheetRequest);
			response.getErrors().add("Período salvo com sucesso");
			return response;
		}
	}

	public Optional<Timesheet> findByID(Long id){
		log.info("Buscando Timesheet por ID: {}", id);
		return this.timesheetRepository.findById(id);
	}
	
	public List<Timesheet> returnTimesheets() {
		List<Timesheet> List = timesheetRepository.findAll();
		return List;

	}

	public void delete(Long id) {
		this.timesheetRepository.deleteById(id);
	}
	
	public Optional<Timesheet> findTimesheetByCollaborator(Timesheet timesheet){
		return this.timesheetRepository.findByStartDateTimeAndEndDateTimeAndCollaborator(timesheet.getStartDateTime(), timesheet.getEndDateTime(), timesheet.getCollaborator());
		
	};

}