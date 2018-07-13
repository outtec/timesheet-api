package br.com.outtec.timesheetapi.dtos;

import java.util.Optional;

import br.com.outtec.timesheetapi.enums.Perfil;

public class CollaboratorDto {
	
	private Optional<Long> id = Optional.empty();
	private String name;
	private String password;
	private Perfil perfil;
	
	public Optional<Long> getId() {
		return id;
	}
	public void setId(Optional<Long> id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}
