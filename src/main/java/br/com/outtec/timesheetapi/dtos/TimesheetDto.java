package br.com.outtec.timesheetapi.dtos;

import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class TimesheetDto {

	private Long id;
	private Date startDateTime;
	private Date endDateTime;
	private Boolean isHoliday;
	private Boolean isInTravel;
	private String periodDescription;

	public TimesheetDto() {}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "A Data e hora de início do período não pode ser vazia")
	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	@NotEmpty(message = "A Data e hora final do período precisa ser informada")
	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
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


}
