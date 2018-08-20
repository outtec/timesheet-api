package br.com.outtec.timesheetapi.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.repositories.TimesheetRepository;
import br.com.outtec.timesheetapi.services.TimesheetService;


@Service
public class TimesheetServiceImpl implements TimesheetService{

	private static final Logger log = LoggerFactory.getLogger(TimesheetServiceImpl.class);
	
	@Autowired
	private TimesheetRepository timesheetRepository;
	
	public Timesheet save(Timesheet obj) {
		System.out.println(obj.getStartDateTime().getTime());
		DateTime startDateTimeObj = new DateTime(obj.getStartDateTime());
		DateTime endDateTimeObj = new DateTime(obj.getEndDateTime());
		Interval interval = new Interval(startDateTimeObj,endDateTimeObj);
		String strTime = interval.toPeriod().getHours()+":"+interval.toPeriod().getMinutes();
		strTime = insereZeroAEsquerda(interval.toPeriod().getHours(),interval.toPeriod().getMinutes());
		obj.setTotalTime(strTime);
		log.info("Persistindo Timesheet: {}", obj);
		return this.timesheetRepository.save(obj); 
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
		return this.timesheetRepository.findByCollaboratorId(id, pageRequest);
	};


	public boolean checkExistingTimesheet(Timesheet obj) {
		Boolean existeTimesheet = false;
		List<Timesheet> list = this.timesheetRepository.findByStartDateTimeAndEndDateTimeAndCollaborator(obj.getStartDateTime(),obj.getEndDateTime(),obj.getCollaborator());
		if (list.isEmpty()){
			return false;
		}
		Timesheet timesheet = list.get(0);
		if(timesheet != null && !obj.equals(timesheet)){
			existeTimesheet = true;
		}
		return existeTimesheet;
	}

	@Override
	public List<Timesheet> findByCollaboratorId(Long id) {
		return this.timesheetRepository.findByCollaboratorId(id);
	}

	public Page<Timesheet> getByPeriod(Date startDate, Date endDate, PageRequest pageRequest) {
		return this.timesheetRepository.findByCollaboratorBetween(startDate, endDate,pageRequest);
	}
	
	public Page<Timesheet> findByCollaboratorIdAndStarDateTime(Long collaboratorId, Date startDateTime, PageRequest pageRequest) {
		return this.timesheetRepository.findByCollaboratorIdAndStartDateTime(collaboratorId, startDateTime, pageRequest);
	}
	
	private String insereZeroAEsquerda(Integer horas, Integer minutos){
		String horaFormatada = "";
		if (horas< 10){
			horaFormatada = "0"+horas+":";
		}
		if (minutos< 10){
			horaFormatada = horaFormatada +"0"+minutos;
		}
		return horaFormatada;
	}

}