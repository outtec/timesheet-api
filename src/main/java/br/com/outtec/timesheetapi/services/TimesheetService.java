package br.com.outtec.timesheetapi.services;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.jni.Time;
import org.springframework.http.ResponseEntity;

import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.dtos.TimesheetDto;
import br.com.outtec.utils.Response;

public interface TimesheetService {
	
	/**
	 * Save a new hours period
	 * @param timehsheet
	 * @return Timesheet
	 */
	Response<Timesheet> save(Timesheet timesheet);
	
	/**
	 * Return a timesheet by ID
	 * @param timesheet
	 * @return Timesheet
	 */
	Optional<Timesheet> findByID(Long id);
	
	/**
	 * List all timesheets entrys
	 * @return List<Timesheet>
	 */
	List<Timesheet> returnTimesheets();
	
	/**
	 * Delete a timesheet by ID
	 * @param id
	 */
	void delete(Long id);
	
	
	/**
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @return
	 */
	Optional<Timesheet> findByTimesheetBetween(Date startDateTime,Date endDateTime);
	
}
