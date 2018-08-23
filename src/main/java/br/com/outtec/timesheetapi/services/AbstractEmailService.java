package br.com.outtec.timesheetapi.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	
	@Override
	public void sendNewPasswordEmail(String email, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(email, newPass);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage prepareNewPasswordEmail(String email, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(email);
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
}