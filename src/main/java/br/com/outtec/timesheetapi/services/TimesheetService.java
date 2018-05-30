package br.com.outtec.timesheetapi.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.utils.Response;

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
	 * Retorna Timehseets por Colaborador
	 * @param id
	 * @return
	 */
	Page<Timesheet> findByCollaboratorId(Long id, PageRequest pageRequest);
	
	/**
	 * 
	 * @param StardDate
	 * @param EndDate
	 * @return
	 */
	Timesheet findByStartDateTimeAndEndDateTime(Date StardDate, Date EndDate);

	Optional<Timesheet> findTimesheetByCollaborator(Timesheet timesheet);
}
