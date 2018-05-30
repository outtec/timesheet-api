package br.com.outtec.timesheetapi.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	public  Page<Timesheet> findByCollaboratorId(Long id, PageRequest pageRequest){
		log.info("Buscando lançamentos para o colaborador ID {}", id);
		return this.timesheetRepository.findByCollaboratorId(id, pageRequest);
	};


	@Override
	public Optional<Timesheet> findTimesheetByCollaborator(Timesheet timesheet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timesheet findByStartDateTimeAndEndDateTime(Date StardDate, Date EndDate) {
		return this.timesheetRepository.findByStartDateTimeAndEndDateTime(StardDate, EndDate);
	}




}