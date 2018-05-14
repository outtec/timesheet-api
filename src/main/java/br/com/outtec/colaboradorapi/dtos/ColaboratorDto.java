package br.com.outtec.colaboradorapi.dtos;

import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

public class ColaboratorDto {
	
	private Optional<Long> id = Optional.empty();
	private String colaborador;
	private Date dataAtualizacao;
	private Date dataCriacao;
	
	public Optional<Long> getId() {
		return id;
	}
	public void setId(Optional<Long> id) {
		this.id = id;
	}
	public String getColaborador() {
		return colaborador;
	}
	@NotEmpty(message = "Informe o nome do Colaborador")
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

}
