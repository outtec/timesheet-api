package br.com.outtec.timesheetapi.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import br.com.outtec.colaboradorapi.domain.Colaborador;


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
	private String colaborador;

	public Timesheet(){}
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
	public String getColaborador() {
		return colaborador;
	}
	public void setColaborador(String colaborador) {
		this.colaborador = colaborador;
	}


}
