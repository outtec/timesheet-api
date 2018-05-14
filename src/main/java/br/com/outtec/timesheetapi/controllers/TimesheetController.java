package br.com.outtec.timesheetapi.controllers;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.dtos.TimesheetDto;
import br.com.outtec.timesheetapi.services.TimesheetService;
import br.com.outtec.utils.Response;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*")
public class TimesheetController {

	private static final Logger log = LoggerFactory.getLogger(TimesheetController.class);

	@Autowired
	private TimesheetService timesheetService;

	public TimesheetController() {}

	/**
	 * Retorna todos os lançamentos
	 * @return Timesheet
	 */
	@GetMapping("/timesheets")
	public ResponseEntity<Response<ArrayList>> timesheets(){
		Response<ArrayList> response = new Response<ArrayList>();
		ArrayList listTimesheet = (ArrayList) timesheetService.retornaTimesheets();
		response.setData(listTimesheet);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna uma entrada cadastrada por ID
	 * @param id
	 * @return Timesheet
	 */
	@GetMapping("/timesheets/{id}")
	public ResponseEntity<Response<TimesheetDto>> getByID(@PathVariable("id") long id){
		Response<TimesheetDto> response = new Response<TimesheetDto>();
		Optional<Timesheet> timesheet = timesheetService.buscaPorID(id);

		//TRATAMENTO DE ERRO
		if(!timesheet.isPresent()) {
			response.getErrors().add("Lançamento não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterTimesheetParaDto(timesheet.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Cadastra um período de horas
	 * @param timesheetDto
	 * @param result
	 * @return ResponseEntity<Response<TimesheetDto>>
	 * @throws NoSuchAlgorithmException
	 * @throws ParseException 
	 *
	 * */
	@PostMapping("/timesheet")
	public ResponseEntity<Response<TimesheetDto>> save(@Valid @RequestBody TimesheetDto timesheetDto,
			BindingResult result) throws NoSuchAlgorithmException, ParseException{

		//TRATAERRO
		Response<TimesheetDto> response = new Response<TimesheetDto>();
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		//SALVANDO ENTRADA DE TIMESHEET
		Timesheet timesheet = this.convertDtoParaTimesheet(timesheetDto, result);
		timesheet = this.timesheetService.save(timesheet);
		response.setData(this.converterTimesheetParaDto(timesheet));
		return ResponseEntity.ok(response);
	}

	/**
	 * Atualiza os dados de um período de horas
	 * @param id
	 * @param timesheetDto
	 * @param result
	 * @return timsheet pós edição
	 * @throws ParseException
	 */
	@PutMapping("/timesheets/{id}")
	public ResponseEntity<Response<TimesheetDto>> update(@PathVariable("id") Long id, @Valid @RequestBody TimesheetDto timesheetDto,
			BindingResult result) throws ParseException{
		Response<TimesheetDto> response = new Response<TimesheetDto>();
		timesheetDto.setId(Optional.of(id));
		Timesheet timesheet = this.convertDtoParaTimesheet(timesheetDto, result);

		//TRATAERRO
		if(result.hasErrors()) {
			log.error("Erro validando lançamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		//SALVANDO TIMEHSEET

		timesheet = this.timesheetService.save(timesheet);
		response.setData(this.converterTimesheetParaDto(timesheet));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Deleta o lançamento 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/timesheets/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		Optional<Timesheet> timesheet = timesheetService.buscaPorID(id);
		
		//TRATAERRO
		if (!timesheet.isPresent()) {
			response.getErrors().add("Erro ao remover lançamento. Registro não encontrado !");
			return ResponseEntity.badRequest().body(response);
		}
		//DELETA LANÇAMENTO
		this.timesheetService.delete(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	//CONVERSÃO DOS DTOS
	private Timesheet convertDtoParaTimesheet(TimesheetDto timesheetDto, BindingResult result) throws ParseException{
		Timesheet timesheet = new Timesheet();
		if (timesheetDto.getId().isPresent()){
			Optional<Timesheet> istimesheet = this.timesheetService.buscaPorID(timesheetDto.getId().get());
			if(istimesheet.isPresent()) {
				timesheet = istimesheet.get();
			}else {
				result.addError(new ObjectError("timesheet", "Lançamento não encontrado. "));
			}
		}
			timesheet.setEndDateTime(timesheetDto.getEndDateTime());
			timesheet.setStartDateTime(timesheetDto.getStartDateTime());
			timesheet.setIsHoliday(timesheetDto.getIsHoliday());
			timesheet.setIsInTravel(timesheetDto.getIsInTravel());
			timesheet.setPeriodDescription(timesheetDto.getPeriodDescription());
			timesheet.setColaborador(timesheetDto.getColaborador());
		return timesheet;
	}
	
	private TimesheetDto converterTimesheetParaDto(Timesheet timesheet) {
		TimesheetDto timesheetDto = new TimesheetDto();
		timesheetDto.setEndDateTime(timesheet.getEndDateTime());
		timesheetDto.setStartDateTime(timesheet.getStartDateTime());
		timesheetDto.setIsHoliday(timesheet.getIsHoliday());
		timesheetDto.setIsInTravel(timesheet.getIsInTravel());
		timesheetDto.setPeriodDescription(timesheet.getPeriodDescription());
		timesheetDto.setColaborador(timesheet.getColaborador());
		return timesheetDto;
	} 

}