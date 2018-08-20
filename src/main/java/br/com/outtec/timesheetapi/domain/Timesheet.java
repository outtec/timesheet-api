package br.com.outtec.timesheetapi.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.joda.time.Hours;

@Entity
public class Timesheet implements Serializable{

	private static final long serialVersionUID = -2358695929722404342L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Date startDateTime;
	private Date endDateTime;
	private Boolean isHoliday;
	private Boolean isInTravel;
	private String periodDescription;
	private Date dataAtualizacao;
	private Date dataCriacao;
	private String totalTime;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "collaborator_id")
	private Collaborator collaborator;

	public Timesheet(){

	}

	public Collaborator getCollaborator() {
		return collaborator;
	}
	public void setCollaborator(Collaborator collaborator) {
		this.collaborator = collaborator;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

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

	public String getPeriodDescription() {
		return periodDescription;
	}
	public void setPeriodDescription(String periodDescription) {
		this.periodDescription = periodDescription;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	@PreUpdate
	public void preUpdate() {
		setDataAtualizacao(new Date());
	}

	@PrePersist
	public void prePersist() {
		final Date atual = new Date();
		setDataCriacao(atual);
		setDataAtualizacao(atual);
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Timesheet other = (Timesheet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Timesheet [id=" + id + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime
				+ ", isHoliday=" + isHoliday + ", isInTravel=" + isInTravel + ", periodDescription=" + periodDescription
				+ ", dataAtualizacao=" + dataAtualizacao + ", dataCriacao=" + dataCriacao + ", collaborator="
				+ collaborator + "]";
	}



}
