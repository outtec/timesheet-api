package br.com.outtec.colaboradorapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import br.com.outtec.colaboradorapi.domain.Colaborador;

@Transactional
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long>{

}
