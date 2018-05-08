package br.com.outtec.timesheetapi.services;


import java.util.Optional;

import br.com.outtec.timesheetapi.domain.Timesheet;

public interface TimesheetService {
	
	/**
	 * Save a new period of hours
	 * @param timehsheet
	 * @return Timesheet
	 */
	Timesheet persist(Timesheet timesheet);
	


}
