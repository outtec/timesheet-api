package br.com.outtec.timesheetapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.outtec.timesheetapi.services.EmailService;
import br.com.outtec.timesheetapi.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {
        
    
    
    @Bean
    public EmailService emailService() {
	return new SmtpEmailService();	
    }
    
}