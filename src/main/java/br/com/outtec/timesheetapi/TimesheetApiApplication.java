package br.com.outtec.timesheetapi;



import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
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
			System.out.println("APP NO AR");

			// interval from start to end
			DateTime start = new DateTime(2004, 12, 25, 17, 37, 0, 0);
			DateTime end = new DateTime(2004, 12, 25, 21, 0, 0, 0);
			Interval interval = new Interval(start, end);
			
			// duration in ms between two instants
			Duration dur = new Duration(start, end);

			// calc will be the same as end
			DateTime calc = start.plus(dur);
			
			System.out.println("Total " + dur);
			System.out.println("INTERVALO ENTRE HORAS " + interval.toPeriod());

			
			/**
			 * 
			DateTime start = interval.getStart();
			DateTime end = interval.getEnd();
			Chronology chrono = interval.getChronology();
			Duration duration = interval.toDuration();
			Period period = interval.toPeriod();
			
			 */
		};          

	}


}
