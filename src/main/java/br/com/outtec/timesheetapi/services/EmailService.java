package br.com.outtec.timesheetapi.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.outtec.timesheetapi.domain.Collaborator;


public interface EmailService {


	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Collaborator collaborator, String newPass);
}