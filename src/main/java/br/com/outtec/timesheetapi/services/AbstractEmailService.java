package br.com.outtec.timesheetapi.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.outtec.timesheetapi.domain.Collaborator;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	
	@Override
	public void sendNewPasswordEmail(Collaborator collaborator, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(collaborator, newPass);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage prepareNewPasswordEmail(Collaborator collaborator, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(collaborator.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
}