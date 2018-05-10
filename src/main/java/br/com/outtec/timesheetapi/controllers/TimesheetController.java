package br.com.outtec.timesheetapi.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.dtos.TimesheetDto;
import br.com.outtec.timesheetapi.responses.Response;
import br.com.outtec.timesheetapi.services.TimesheetService;

@RestController
@RequestMapping("api/v1/timesheets")
public class TimesheetController {
	
	private static final Logger log = LoggerFactory.getLogger(TimesheetController.class);
	
	@Autowired
	private TimesheetService timesheetService;
	
	public TimesheetController() {}
	
	/**
	 * Cadastra um período de horas por data
	 * @param timesheetDto
	 * @param result
	 * @return ResponseEntity<Response<TimesheetDto>>
	 * @throws NoSuchAlgorithmException
	 *
	 * */
	@PostMapping
	public ResponseEntity<Response<TimesheetDto>> persist(@Valid @RequestBody TimesheetDto timesheetDto,
			BindingResult result) throws NoSuchAlgorithmException{
		
		Timesheet timesheet = this.convertDtoParaTimesheet(timesheetDto);
		Response<TimesheetDto> response = new Response<TimesheetDto>();
		//TRATAERRO
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.timesheetService.persist(timesheet);
		response.setData(timesheetDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 *Popula Dto com dados do timesheet
	 * @param timesheetDto
	 * @param result
	 * @return Timesheet
	 * @throws NoSuchAlgorithmException
	 */
	private Timesheet convertDtoParaTimesheet(TimesheetDto timesheetDto){
		Timesheet timesheet = new Timesheet();
		timesheet.setEndDateTime(timesheetDto.getEndDateTime());
		timesheet.setStartDateTime(timesheetDto.getStartDateTime());
		timesheet.setIsHoliday(timesheetDto.getIsHoliday());
		timesheet.setIsInTravel(timesheetDto.getIsInTravel());
		timesheet.setPeriodDescription(timesheetDto.getPeriodDescription());
		
		return timesheet;
	}
	
}