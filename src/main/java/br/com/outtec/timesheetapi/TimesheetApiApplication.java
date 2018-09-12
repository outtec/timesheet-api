package br.com.outtec.timesheetapi;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.outtec.timesheetapi.repositories.TimesheetRepository;
import br.com.outtec.timesheetapi.services.TimesheetService;


@SpringBootApplication
public class TimesheetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimesheetApiApplication.class, args);
	}

	@Autowired
	TimesheetRepository repo;

	@Autowired
	TimesheetService service;
	
	//"2018-09-05 21:00:00"
	Long ID = 153L;
	DateTime start = new DateTime(2018,9,5,0,0); 
	DateTime end = new DateTime(2018,9,8,0,0);

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {

		System.out.println(service.getTimesheetsPorPeriodo(ID, start, end).toString());
			


		};
	}


}