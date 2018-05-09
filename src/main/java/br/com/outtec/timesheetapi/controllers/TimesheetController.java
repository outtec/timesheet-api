package br.com.outtec.timesheetapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.outtec.timesheetapi.dtos.TimesheetDto;
import br.com.outtec.timesheetapi.responses.Response;

@RestController
@RequestMapping("api/v1/timesheets")
public class TimesheetController {
	@GetMapping(value = "/{time}")
	public String exemplo(@PathVariable("time") String time) {
		return "API de Timesheet exposta ;) " + time;
	}
	
	@PostMapping
	public ResponseEntity<Response<TimesheetDto>> persist(@RequestBody TimesheetDto timesheetDto){
		Response<TimesheetDto> response = new Response<TimesheetDto>();

		response.setData(timesheetDto);
		return ResponseEntity.ok(response);
	}
}
