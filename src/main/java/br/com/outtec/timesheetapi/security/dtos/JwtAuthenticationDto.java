package br.com.outtec.timesheetapi.security.dtos;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;

	public JwtAuthenticationDto() {
	}
	@NotEmpty(message = "Email não pode ser vazio.")
	@Email(message = "Email inválido.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "Senha não pode ser vazia.")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
