package br.com.outtec.timesheetapi.services;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.com.outtec.timesheetapi.domain.Timesheet;

public interface TimesheetService {
	
	/**
	 * Save a new hours period
	 * @param timehsheet
	 * @return Timesheet
	 */
	Timesheet save(Timesheet timesheet);
	
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
	 * @return Timesheet
	 */
	Optional<Timesheet> findTimehseetByCollaborator(Date startDateTime,Date endDateTime,String colaborador);
}
