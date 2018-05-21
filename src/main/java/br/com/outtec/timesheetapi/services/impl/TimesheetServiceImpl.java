package br.com.outtec.timesheetapi.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	public Response<Timesheet> save(Timesheet timesheet) {
		Response<Timesheet> response = new Response<Timesheet>();
		log.info("Persistindo Timesheet: {}", timesheet);
		if (timesheet.getId() == null) {
			if(this.timesheetRepository.findByStartDateTimeAndEndDateTimeAndCollaborator(timesheet.getStartDateTime(), timesheet.getEndDateTime(), timesheet.getCollaborator()).isPresent()){
				response.getErrors().add("Já existe um período cadastrado com a Data de Entrada, Saída e Horários que foram fornecidos.");
				return response;
			}
		}	
		this.timesheetRepository.save(timesheet);
		response.getErrors().add("Período salvo com sucesso");
		return response;
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

	public Optional<Timesheet> findByTimesheetBetween(Date startDateTime, Date endDateTime) {
		return this.timesheetRepository.findByCollaboratorBetween(endDateTime, startDateTime);
	}

}