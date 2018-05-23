package br.com.outtec.timesheetapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.outtec.utils.PasswordUtils;


@SpringBootApplication
public class TimesheetApiApplication {
	


	public static void main(String[] args) {
		SpringApplication.run(TimesheetApiApplication.class, args);
	}
	private static final String PASSWORD = "whereIStheLsS?";

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {

			String encodedPassword = PasswordUtils.getBCrypt(PASSWORD);
			System.out.println("Password encoded : " + encodedPassword);
			
			encodedPassword = PasswordUtils.getBCrypt(PASSWORD);
			System.out.println("Password encoded hash again : " + encodedPassword);
			
			encodedPassword = PasswordUtils.getBCrypt(PASSWORD);
			System.out.println("Valid Password : " + PasswordUtils.isValidPassword(PASSWORD, encodedPassword));
			
			
		};
}
}
