package br.com.outtec.timesheetapi.services;

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
	private EmailService emailService;
	
	private String nPass;
	
	public void sendNewPassword(String email) throws ObjectNotFoundException {
		
		Collaborator collaborator = collaboratorRepository.findByEmail(email);
		if (collaborator == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		collaborator.setPassword(PasswordUtils.getBCrypt(newPass));
		
		collaboratorRepository.save(collaborator);
		emailService.sendNewPasswordEmail(collaborator, newPass);
	}

	private String newPassword() {
		nPass = "PASSWORDNOVO";
		return nPass;
	}


}