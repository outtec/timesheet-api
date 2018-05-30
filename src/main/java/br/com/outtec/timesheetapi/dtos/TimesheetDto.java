package br.com.outtec.timesheetapi.dtos;

import java.util.Date;
import java.util.Optional;
import org.hibernate.validator.constraints.Length;


public class TimesheetDto {

	private Optional<Long> id = Optional.empty();
	private Date startDateTime;
	private Date endDateTime;
	private Boolean isHoliday;
	private Boolean isInTravel;
	private String periodDescription;
	private Long collaboratorId;
	

	public TimesheetDto() {}

	public Optional<Long> getId() {
		return id;
	}
	public void setId(Optional<Long> id) {
		this.id = id;
	}

	//@NotEmpty(message = "A Data e hora de início do período não pode ser vazia")
	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	//@NotEmpty(message = "A Data e hora final do período precisa ser informada")
	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date date) {
		this.endDateTime = date;
	}

	public Boolean getIsHoliday() {
		return isHoliday;
	}
	public void setIsHoliday(Boolean isHoliday) {
		this.isHoliday = isHoliday;
	}
	public Boolean getIsInTravel() {
		return isInTravel;
	}
	public void setIsInTravel(Boolean isInTravel) {
		this.isInTravel = isInTravel;
	}
	@Length(min = 10, max = 500, message ="Descrição do Periodo deve conter entre 10 e 500 caracteres.")
	public String getPeriodDescription() {
		return periodDescription;
	}

	public void setPeriodDescription(String periodDescription) {
		this.periodDescription = periodDescription;
	}

	public Long getCollaboratorId() {
		return collaboratorId;
	}

	public void setCollaboratorId(Long collaboratorId) {
		this.collaboratorId = collaboratorId;
	}
	


}
