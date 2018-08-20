package br.com.outtec.timesheetapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.outtec.timesheetapi.services.EmailService;
import br.com.outtec.timesheetapi.services.MockEmailService;

@Configuration
@Profile("dev")
public class TestConfig {

//    @Autowired
//    private DBService dbService;
//    
//    @Bean
//    public boolean instantiateDatabase() throws ParseException {
//	dbService.instantiateDatabase();
//	return true;
//    }
    
    @Bean
    public EmailService emailService() {
//	return new SmtpEmailService();
	return new MockEmailService();	
    }
    
}