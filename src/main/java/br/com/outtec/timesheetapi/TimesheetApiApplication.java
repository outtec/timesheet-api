package br.com.outtec.timesheetapi;

import org.joda.time.DateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TimesheetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimesheetApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			String dated = "10/09/2018";
			int DATE = Integer.parseInt(dated.substring(0, 2));
			int MONTH = Integer.parseInt(dated.substring(3, 5));
			int YEAR = Integer.parseInt(dated.substring(6, 10));

			
			System.err.println(dated.substring(0, 2));
			System.err.println(dated.substring(3, 5));
			System.err.println(dated.substring(6, 10));
			DateTime dtt = new DateTime(YEAR,MONTH,DATE,0,0); 
			System.err.println(dtt.toDate());

		};
	}

}