package br.com.outtec.timesheetapi.controllers;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.dtos.TimesheetDto;
import br.com.outtec.timesheetapi.services.TimesheetService;
import br.com.outtec.utils.Response;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
public class TimesheetController {

	private static final Logger log = LoggerFactory.getLogger(TimesheetController.class);

	DateFormat dateformat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@Autowired
	private TimesheetService timesheetService;

	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	public TimesheetController() {}

	@GetMapping("timesheets")
	public ResponseEntity<Response<Page<TimesheetDto>>> getTimesheetsByCollaboratorId(
			@RequestParam(value = "collaboratorid") long collaboratorId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		log.info("Buscando lançamentos por ID do colaborador: {}, página: {}", collaboratorId, pag);
		Response<Page<TimesheetDto>> response = new Response<Page<TimesheetDto>>();
		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina,Direction.valueOf(dir), ord);
		Page<Timesheet> timesheets = this.timesheetService.findByCollaboratorId(collaboratorId, pageRequest);
		Page<TimesheetDto> timesheetDto = timesheets.map(timesheet -> this.converterTimesheetParaDto(timesheet));
		response.setData(timesheetDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna uma entrada cadastrada por ID
	 * @param id
	 * @return Timesheet
	 */
	@GetMapping("timesheets/{id}")
	public ResponseEntity<Response<TimesheetDto>> getByID(@PathVariable("id") long id){
		log.info("Buscando lançamento por ID: {}", id);
		Response<TimesheetDto> response = new Response<TimesheetDto>();
		Optional<Timesheet> timesheet = timesheetService.findByID(id);

		//TRATAMENTO DE ERRO
		if(timesheet == null) {
			response.getErrors().add("Lançamento não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterTimesheetParaDto(timesheet.get()));
		return ResponseEntity.ok(response);
	}

	/**
	 * Cadastra um novo período de horas
	 * @param Período ou Lançamento (timesheetDto)
	 * @param result
	 * @return ResponseEntity<Response<TimesheetDto>>
	 * @throws ParseException 
	 *
	 * */
	@PostMapping("timesheets") 
	public ResponseEntity<Response<TimesheetDto>> save(@Valid @RequestBody TimesheetDto timesheetDto, 
			BindingResult result) throws ParseException{ 
		log.info("Adicionando lançamento: {}", timesheetDto.toString()); 
		Response<TimesheetDto> response = new Response<TimesheetDto>(); 
		Timesheet timesheet = this.convertDtoParaTimesheet(timesheetDto, result);
		//TRATAERRO 
		if(result.hasErrors()) { 
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage())); 
			return ResponseEntity.badRequest().body(response); 
		} 
		if(this.timesheetService.checkExistingTimesheet(timesheet)) {
			response.getErrors().add("Já existe uma entrada cadastrada com os dados informados");
			return ResponseEntity.badRequest().body(response);
		}else {
			//SALVANDO ENTRADA DE TIMESHEET 
			timesheet = this.timesheetService.save(timesheet); 
			response.setData(this.converterTimesheetParaDto(timesheet)); 
			return ResponseEntity.ok(response);
		}
	}
	/**
	 * 
	 * @param obj
	 * @param result
	 * @return
	 * @throws ParseException
	**/
	/**
	 * 
	@PostMapping("timesheets")
	public ResponseEntity<Void> insert(@Valid @RequestBody TimesheetDto obj, BindingResult result) throws ParseException {
		Timesheet timesheet = this.timesheetService.save(this.convertDtoParaTimesheet(obj,result));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	*/

	
	/**
	 * Atualiza os dados de um período de horas
	 * @param id
	 * @param timesheetDto
	 * @param result
	 * @return timsheet
	 * @throws ParseException
	 */
	@PutMapping("timesheets/{id}")
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

		if(this.timesheetService.checkExistingTimesheet(timesheet)) {
			response.getErrors().add("Já existe uma entrada cadastrada com os dados informados");
			return ResponseEntity.badRequest().body(response);
		}else {
			//SALVANDO ENTRADA DE TIMESHEET 
			timesheet = this.timesheetService.save(timesheet); 
			response.setData(this.converterTimesheetParaDto(timesheet)); 
			return ResponseEntity.ok().body(response);
		}
	}

	/**
	 * Deleta um lançamento 
	 * @param id
	 * @return response
	 */
	@DeleteMapping("timesheets/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id){
		Response<String> response = new Response<String>();
		Optional<Timesheet> timesheet = timesheetService.findByID(id);

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
			Optional<Timesheet> ts = this.timesheetService.findByID(timesheetDto.getId().get());
			if(ts.isPresent()) {
				timesheet = ts.get();
			}else {
				result.addError(new ObjectError("timesheet", "Lançamento não encontrado. "));
			}
		}else {
			timesheet.setCollaborator(new Collaborator());
			timesheet.getCollaborator().setId(timesheetDto.getCollaboratorId());
		}
		
		Date endDate = simpleDateFormat.parse(timesheetDto.getEndDateTime());
		Date startDate = simpleDateFormat.parse(timesheetDto.getStartDateTime());
		
		timesheet.setEndDateTime(endDate);
		timesheet.setStartDateTime(startDate);
		timesheet.setIsHoliday(timesheetDto.getIsHoliday());
		timesheet.setIsInTravel(timesheetDto.getIsInTravel());
		timesheet.setPeriodDescription(timesheetDto.getPeriodDescription());

		return timesheet;
	}

	private TimesheetDto converterTimesheetParaDto(Timesheet timesheet) {
		TimesheetDto timesheetDto = new TimesheetDto();
		timesheetDto.setId(Optional.of(timesheet.getId()));
		timesheetDto.setEndDateTime(dateformat.format(timesheet.getEndDateTime()));
		timesheetDto.setStartDateTime(dateformat.format(timesheet.getStartDateTime()));
		timesheetDto.setIsHoliday(timesheet.getIsHoliday());
		timesheetDto.setIsInTravel(timesheet.getIsInTravel());
		timesheetDto.setPeriodDescription(timesheet.getPeriodDescription());
		timesheetDto.setCollaboratorId(timesheet.getCollaborator().getId());

		return timesheetDto;
	} 

}