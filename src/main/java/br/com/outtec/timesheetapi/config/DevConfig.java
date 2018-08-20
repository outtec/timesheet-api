package br.com.outtec.timesheetapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.outtec.timesheetapi.services.EmailService;
import br.com.outtec.timesheetapi.services.MockEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
    
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;
    
    @Bean
    public EmailService emailService() {
//	return new SmtpEmailService();
	return new MockEmailService();	
    }
    
}