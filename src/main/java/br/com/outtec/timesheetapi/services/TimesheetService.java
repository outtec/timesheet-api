package br.com.outtec.timesheetapi.services;


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
	Optional<Timesheet> buscaPorID(Long id);
	
	/**
	 * List all timesheets entrys
	 * @return List<Timesheet>
	 */
	List<Timesheet> retornaTimesheets();
	
	/**
	 * Delete a timesheet by ID
	 * @param id
	 */
	void delete(Long id);

}
