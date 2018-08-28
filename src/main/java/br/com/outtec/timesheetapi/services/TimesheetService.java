package br.com.outtec.timesheetapi.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
	 * Return Timehseets by Collaborator Paginado
	 * @param id
	 * @return
	 */
	Page<Timesheet> findByCollaboratorId(Long id, PageRequest pageRequest);
	
	/**
	 * Return Timehseets by Collaborator
	 * @param id
	 * @return
	 */
	List<Timesheet> findByCollaboratorId(Long id);
	
	/**
	 * 
	 * @param timesheet
	 * @return
	 */
	boolean checkExistingTimesheet(Timesheet obj);
	
	/**
	 * 
	 * @param id
	 * @param starDateTime
	 * @return A list of timessheets by Day
	 */
	Page<Timesheet> findByCollaboratorIdAndStarDateTime(Long id, Date startDateTime,PageRequest pageRequest);

	String insereZeroAEsquerda(Integer horas, Integer minutos);
	
}
