package br.com.outtec.colaboradorapi.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.outtec.colaboradorapi.domain.Colaborador;
import br.com.outtec.colaboradorapi.repositories.ColaboradorRepository;
import br.com.outtec.colaboradorapi.services.ColaboradorService;

public class ColaboradorServcicesImpl implements ColaboradorService{

	private static final Logger log = LoggerFactory.getLogger(ColaboradorServcicesImpl.class);

	@Autowired
	private ColaboradorRepository colaboradorRepository;

	@Override
	public Colaborador save(Colaborador colaborador) {
		log.info("Persistindo Colaborador: {}", colaborador);
		return colaboradorRepository.save(colaborador);
	}
	
	
}
