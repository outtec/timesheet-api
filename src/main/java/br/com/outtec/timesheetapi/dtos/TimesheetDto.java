package br.com.outtec.timesheetapi.dtos;

import java.util.Optional;


public class TimesheetDto {

	private Optional<Long> id = Optional.empty();
	private String startDateTime;
	private String endDateTime;
	private Boolean isHoliday;
	private Boolean isInTravel;
	private String periodDescription;
	private Long collaboratorId;
	private String totalTime;
	private String timeUtil;
	private String timeNormal;

	public String getTimeUtil() {
		return timeUtil;
	}

	public void setTimeUtil(String timeUtil) {
		this.timeUtil = timeUtil;
	}

	public String getTimeNormal() {
		return timeNormal;
	}

	public void setTimeNormal(String timeNormal) {
		this.timeNormal = timeNormal;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public TimesheetDto() {}

	public Optional<Long> getId() {
		return id;
	}
	public void setId(Optional<Long> id) {
		this.id = id;
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

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

}
