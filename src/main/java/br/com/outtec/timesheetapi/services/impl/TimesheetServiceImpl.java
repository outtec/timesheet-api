package br.com.outtec.timesheetapi.services.impl;

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


	public Timesheet persist(Timesheet timesheet) {
		log.info("Persistindo Timesheet: {}", timesheet);
		return this.timesheetRepository.save(timesheet);
	}

	public Optional<Optional<Timesheet>> buscaPorID(Long id){
		log.info("Buscando Timesheet por ID: {}", id);
		return Optional.ofNullable(this.timesheetRepository.findById(id));

	}

}