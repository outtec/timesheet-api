package br.com.outtec.timesheetapi;

import java.util.Date;

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
	Long ID = 1L;
//	DateTime start = new DateTime(2018,9,10,0,0); 
//	DateTime end = new DateTime(2018,9,13,0,0);
	Date start = new Date(2018,9,10,0,0); 
	Date end = new Date(2018,9,13,0,0);

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
String  s1 = "oi";
String  s2 = new String("oi");
System.out.println(s1.equals(s2));

//		System.out.println(service.getTimesheetsByPeriod(ID, start, end).toString());
			


		};
	}


}