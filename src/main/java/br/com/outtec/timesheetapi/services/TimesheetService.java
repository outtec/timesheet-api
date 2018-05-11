package br.com.outtec.timesheetapi.services;


import java.util.List;
import java.util.Optional;

import br.com.outtec.timesheetapi.domain.Timesheet;

public interface TimesheetService {
	
	/**
	 * Save a new period of hours
	 * @param timehsheet
	 * @return Timesheet
	 */
	Timesheet persist(Timesheet timesheet);
	
	/**
	 * Retorna uma entrada Timesheet por ID
	 * @param timesheet
	 * @return Timesheet
	 */
	Optional<Timesheet> buscaPorID(Long id);
	
	/**
	 * Lista de todas as entradas 
	 * @return List<Timesheet>
	 */
	List<Timesheet> retornaTimesheets();

}
