package br.com.outtec.timesheetapi.dtos;

import java.io.Serializable;

public class RuleDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id ;
	private String name;
	private String value;
	private Long collaboratorId;
	
	public Long getCollaboratorId() {
		return collaboratorId;
	}

	public void setCollaboratorId(Long collaboratorId) {
		this.collaboratorId = collaboratorId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	
	
}
