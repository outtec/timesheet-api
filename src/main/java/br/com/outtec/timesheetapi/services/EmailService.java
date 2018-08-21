package br.com.outtec.timesheetapi.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(String email, String newPass);
}