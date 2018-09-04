package br.com.outtec.timesheetapi;

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


	Long ID = 153L;


	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
		
		service.calculaHoraForaDeHorarioComercial(ID);
		//service.calculaHoraForaDeHorarioComercial(ID,21,23);

		};
	}


}