package br.com.outtec.colaboradorapi.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Colaborador {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	private String colaborador;
	private Date dataAtualizacao;
	private Date dataCriacao;
	
	public Colaborador(){}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getColaborador() {
		return colaborador;
	}
	public void setColaborador(String colaborador) {
		this.colaborador = colaborador;
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
}


