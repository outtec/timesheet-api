package br.com.outtec.timesheetapi;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.outtec.timesheetapi.domain.Timesheet;
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


	Long ID = 147L;
	String nome;
	DateTime startDate;// = new DateTime(2000,1,1,17,23);
	DateTime endDate;// = new DateTime(2000,1,1,22,59);
	DateTime timeInit = new DateTime(2018,8,27,21,0);
	DateTime timeEnd = new DateTime(2000,8,27,23,59);

	Interval intervalAfter21;
	Period objPeriodAfter21;
	Period totalAfter21 = new Period(0);
	String timeAfter21;

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {

			List<Timesheet> lancamentos = this.repo.findByCollaboratorId(ID);
			lancamentos.stream().forEach(lancamento ->{ 
				startDate = new DateTime(lancamento.getStartDateTime());
				endDate = new DateTime(lancamento.getEndDateTime());

				if(startDate.isAfter(timeInit)){

					intervalAfter21 = new Interval(startDate,endDate);
					objPeriodAfter21 = new Period(intervalAfter21);
					totalAfter21 = totalAfter21.plusHours(objPeriodAfter21.getHours());
					totalAfter21 = totalAfter21.plusMinutes(objPeriodAfter21.getMinutes());		
					timeAfter21 = service.insereZeroAEsquerda(objPeriodAfter21.getHours(),objPeriodAfter21.getMinutes());
				}

				if(startDate.isBefore(timeInit) && (endDate.isAfter(timeInit))){
					//Pega do tempo inicial até as 21h
					intervalAfter21 = new Interval(startDate,timeInit);
					objPeriodAfter21 = new Period(intervalAfter21);
					timeAfter21 = service.insereZeroAEsquerda(objPeriodAfter21.getHours(),objPeriodAfter21.getMinutes());

					//Pega de 21h até o tempo final
					Interval intervalABefore21 = new Interval(timeInit,endDate);
					Period objPeriodBefore21 = new Period(intervalABefore21);
					String timeBefore21 = service.insereZeroAEsquerda(objPeriodBefore21.getHours(),objPeriodBefore21.getMinutes());
					totalAfter21 = totalAfter21.plusHours(objPeriodBefore21.getHours());
					totalAfter21 = totalAfter21.plusMinutes(objPeriodBefore21.getMinutes());
			}
			});
			System.out.println("TOTAL "+totalAfter21);

		};
	}


}