package br.com.outtec.timesheetapi.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.dtos.CollaboratorDto;
import br.com.outtec.timesheetapi.enums.Perfil;
import br.com.outtec.timesheetapi.repositories.CollaboratorRepository;
import br.com.outtec.timesheetapi.security.JwtUser;
import br.com.outtec.timesheetapi.security.JwtUserFactory;
import br.com.outtec.timesheetapi.services.CollaboratorService;
import br.com.outtec.timesheetapi.services.exceptions.AuthorizationException;
import br.com.outtec.timesheetapi.services.exceptions.DataIntegrityException;
import br.com.outtec.utils.PasswordUtils;
import javassist.tools.rmi.ObjectNotFoundException;


@Service
public class CollaboratorServiceImpl  implements CollaboratorService{
	
	private static final Logger log = LoggerFactory.getLogger(CollaboratorServiceImpl.class);
	
	@Autowired
	private CollaboratorRepository repo;
	
public Collaborator find(Long id) throws ObjectNotFoundException {
		
		JwtUser user = JwtUserFactory.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Collaborator> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Collaborator.class.getName()));
	}
	
	
	@Transactional
	public Collaborator insert(Collaborator obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}
	
	public Collaborator update(Collaborator obj) throws ObjectNotFoundException {
		Collaborator newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Long id) throws ObjectNotFoundException {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há lançamentos relacionados");
		}
	}
	
	public List<Collaborator> findAll() {
		return repo.findAll();
	}
	
	public Collaborator findByEmail(String email) throws ObjectNotFoundException{
		JwtUser user = JwtUserFactory.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
	
		Collaborator obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Collaborator.class.getName());
		}
		return obj;
	}
	
	public Page<Collaborator> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Collaborator fromDTO(CollaboratorDto objDto) {
		return new Collaborator(objDto.getId(), objDto.getName(), objDto.getEmail(), PasswordUtils.getBCrypt((objDto.getPassword())));
	}


	private void updateData(Collaborator newObj, Collaborator obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
	

	
}
