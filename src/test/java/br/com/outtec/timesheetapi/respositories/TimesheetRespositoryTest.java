package br.com.outtec.timesheetapi.respositories;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.repositories.TimesheetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles("test")
public class TimesheetRespositoryTest {
	
	@Autowired
	private TimesheetRepository timehseetRepository;
	
	private static final String COLABORADOR = "Joyce Aquino Lima";
	
	@Before 
	public void setUp() throws Exception{
		Timesheet timesheet = new Timesheet();
		timesheet.setStartDateTime(new Date());
		timesheet.setEndDateTime(new Date());
		timesheet.setCollaborator("Joyce Aquino Lima");
		timesheet.setIsHoliday(false);
		timesheet.setIsInTravel(true);
		timesheet.setPeriodDescription("Testando direto no respository com Junit");
		this.timehseetRepository.save(timesheet);
	} 
	/**
	 * 
	@After
	public final void tearDown() {
		this.timehseetRepository.deleteAll();
	}
	
	 */
	
	@Test
	public void testFindByColaborador() {
		Timesheet timehseet = this.timehseetRepository.findByCollaborator(COLABORADOR);
		
		assertEquals(COLABORADOR, timehseet.getCollaborator());
	}
	
	@Test
	public void testFindByStartDateTime(){
		
	}
}