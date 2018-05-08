package br.com.outtec.timesheetapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.repositories.TimesheetRepository;


@SpringBootApplication
public class TimesheetApiApplication {
	
	@Autowired
	private TimesheetRepository timeSheetRepository;

	public static void main(String[] args) {
		SpringApplication.run(TimesheetApiApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			Timesheet time = new Timesheet();
			time.setIsHoliday(false);
			time.setIsInTravel(false);
			this.timeSheetRepository.save(time);
			
			System.out.println("Entrada " + time);
			
			
		};
}
}
