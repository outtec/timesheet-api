package br.com.outtec.timesheetapi.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.repositories.CollaboratorRepository;
import br.com.outtec.timesheetapi.services.exceptions.ObjectNotFoundException;
import br.com.outtec.utils.PasswordUtils;

@Service
public class AuthService {

	@Autowired
	private CollaboratorRepository collaboratorRepository;
	
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) throws ObjectNotFoundException {
		Collaborator collaborator = collaboratorRepository.findByEmail(email);
		if (collaborator == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		collaborator.setPassword(PasswordUtils.getBCrypt(newPass));
		collaboratorRepository.save(collaborator);
		emailService.sendNewPasswordEmail(collaborator.getEmail(), newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}


}
}