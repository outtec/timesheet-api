package br.com.outtec.timesheetapi;



import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import org.xmlunit.util.Convert;


@SpringBootApplication
public class TimesheetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimesheetApiApplication.class, args);
	}

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); //"dd/MM/yyyy HH:mm"


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

			System.out.println("Total " + dur);
			System.out.println("INTERVALO ENTRE HORAS " + interval.toPeriod());
			
			  // A string of time information
	        String time = "15:30:18";
	        //Date and Time: Thu Jan 01 15:30:18 BRT 1970

	        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
	        try {	            
	        	Date date = sdf.parse(time);
	        //	Calendar date = new Calendar(sdf.parse(time));
			//	DateTime start = new DateTime(0, 0, 0, date.getHours(), date.getMinutes(), 0, 0);

	        	System.out.println("Date and Time: " + date);
	        } catch (Exception e) {
	            e.printStackTrace();
	            
	        }
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
