package br.com.outtec.colaboradorapi.services;

import br.com.outtec.colaboradorapi.domain.Colaborador;


public interface ColaboradorService {
	
	/**
	 * Save a new Colaborador
	 * @param colaborador
	 * @return Colaborador
	 */
	Colaborador save(Colaborador colaborador);
	
	
	
}
