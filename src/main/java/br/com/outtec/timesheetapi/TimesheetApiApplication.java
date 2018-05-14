package br.com.outtec.timesheetapi;

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

			
		};
}
}
