package br.com.outtec.timesheetapi.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.dtos.TimesheetDto;
import br.com.outtec.timesheetapi.services.TimesheetService;
import br.com.outtec.utils.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class TimesheetControllerTest {
	
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private TimesheetService timehseetService;

	private static final String URL_API = "api/v1/timesheets";
	private static final long ID_TIMESHEET = Long.valueOf(1);
	private static final long COLLABORATOR_ID  = Long.valueOf(1);
	private static final Collaborator COLLABORATOR = new Collaborator();
	private static final String PERIOD_DESCRIPTION = "Descrição de periodo com Junit test";
	private static final Date DATA = new Date();

	@Test
	public void testSaveTimesheet() throws Exception{
		Timesheet timesheet = getTimesheetData();

		BDDMockito.given(timehseetService.findByID(Mockito.anyLong())).willReturn(Optional.of(new Timesheet()));
		//BDDMockito.given(timehseetService.save(Mockito.any(Timesheet.class))).willReturn();

		mvc.perform(MockMvcRequestBuilders.post(URL_API)
				.content(this.getJsonRequestPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_TIMESHEET))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	DateFormat format = DateFormat.getDateTimeInstance();
	
	private String getJsonRequestPost() throws JsonProcessingException {
		TimesheetDto timesheetDto = new TimesheetDto();
		timesheetDto.setId(null);
		timesheetDto.setCollaboratorId(COLLABORATOR_ID);
		timesheetDto.setStartDateTime(this.dateFormat.format(DATA));
		timesheetDto.setEndDateTime(this.dateFormat.format(DATA));
		timesheetDto.setIsHoliday(false);
		timesheetDto.setIsInTravel(false);
		timesheetDto.setPeriodDescription(PERIOD_DESCRIPTION);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(timesheetDto);
	}

	private Timesheet getTimesheetData() {
		Timesheet timesheet = new Timesheet();
		timesheet.setId(ID_TIMESHEET);
		timesheet.setCollaborator(COLLABORATOR);
		timesheet.setStartDateTime(DATA);
		timesheet.setEndDateTime(DATA);
		timesheet.setIsHoliday(false);
		timesheet.setIsInTravel(false);
		timesheet.setPeriodDescription(PERIOD_DESCRIPTION);
		return timesheet;
	}


}
