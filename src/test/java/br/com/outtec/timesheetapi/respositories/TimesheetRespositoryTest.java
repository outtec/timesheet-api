package br.com.outtec.timesheetapi.respositories;


import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.enums.PerfilEnum;
import br.com.outtec.timesheetapi.repositories.CollaboratorRepository;
import br.com.outtec.timesheetapi.repositories.TimesheetRepository;
import br.com.outtec.utils.PasswordUtils;


@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles("test")
public class TimesheetRespositoryTest {
	
	@Autowired
	private TimesheetRepository timehseetRepository;

	@Autowired
	private CollaboratorRepository collaboratorRepository;

	Timesheet timesheet = new Timesheet();
	Collaborator collaborator =  new Collaborator();
	
	@Before
	public void setUp()throws Exception{
		collaborator =  this.collaboratorRepository.save(getCollaboratorData());

		timesheet = this.timehseetRepository.save(getTimesheetData(collaborator));
		this.timehseetRepository.save(getTimesheetData1(collaborator));
	}
	
	
	@Test
	public void testfindByCollaboratorId() {
		List<Timesheet> timesheets = this.timehseetRepository.findByCollaboratorId(collaborator.getId());
		System.out.println("TEST FUNC BY COLABORADOR");
		System.out.println("Lançamentos do colaborador :" + timesheets.get(0).getCollaborator().getName());
		System.out.println("------ Descrição dos Lançamentos ------");
		System.out.println(timesheets.get(0).getPeriodDescription());
		System.out.println(timesheets.get(1).getPeriodDescription());
		assertEquals(2,timesheets.size());
	}

	@Test
	public void testfindByStartDateTimeAndEndDateTimeAndCollaborator(){
		List<Timesheet> timesheets = this.timehseetRepository.findByStartDateTimeAndEndDateTimeAndCollaborator(timesheet.getStartDateTime(), timesheet.getEndDateTime(),collaborator);
		assertEquals(1,timesheets.size());	
	}
	
	private Timesheet getTimesheetData(Collaborator collaborator) {
		Timesheet timesheet = new Timesheet();
		timesheet.setCollaborator(collaborator);
		timesheet.setStartDateTime(new Date());
		timesheet.setEndDateTime(new Date());
		timesheet.setIsHoliday(false);
		timesheet.setIsInTravel(false);
		timesheet.setPeriodDescription("DESC TESTE 1 TESTANDO REPOSITORIO DO TIMEHSEET COM JUNIT");
		return timesheet;
	}

	private Timesheet getTimesheetData1(Collaborator collaborator) {
		Timesheet ts = new Timesheet();
		ts.setCollaborator(collaborator);
		ts.setStartDateTime(new Date());
		ts.setEndDateTime(new Date());
		ts.setIsHoliday(false);
		ts.setIsInTravel(false);
		ts.setPeriodDescription("T2 DESC TESTANDO REPOSITORIO DO TIMEHSEET COM JUNIT");
		return ts;
	}

	private Collaborator getCollaboratorData()throws NoSuchAlgorithmException{
		Collaborator collaborator = new Collaborator();
		collaborator.setName("Josenildo");
		collaborator.setPassword(PasswordUtils.getBCrypt("Senha123"));
		collaborator.setPerfil(PerfilEnum.ROLE_USER);
		return collaborator;
	}

}