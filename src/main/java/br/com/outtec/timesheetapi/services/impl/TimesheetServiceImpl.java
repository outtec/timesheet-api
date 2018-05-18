package br.com.outtec.timesheetapi.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.repositories.TimesheetRepository;
import br.com.outtec.timesheetapi.services.TimesheetService;


@Service
public class TimesheetServiceImpl implements TimesheetService{

	private static final Logger log = LoggerFactory.getLogger(TimesheetServiceImpl.class);


	@Autowired
	private TimesheetRepository timesheetRepository;

	public Timesheet save(Timesheet timesheet) {
		log.info("Persistindo Timesheet: {}", timesheet);
		return this.timesheetRepository.save(timesheet);
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
	
	public Optional<Timesheet> findTimehseetByCollaborator(Date startDateTime, Date endDateTime,String colaborador) {
		return this.timesheetRepository.findByStartDateTimeAndEndDateTimeAndColaborador(startDateTime, endDateTime,colaborador);
	}


}