package br.com.outtec.timesheetapi.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import br.com.outtec.timesheetapi.enums.PerfilEnum;

@Entity
public class Collaborator implements Serializable {
	

	private static final long serialVersionUID = -242771507735786282L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	private String name;
	private String password;
	private PerfilEnum perfil;
	private List<Timesheet> timehseets;

	public Collaborator() {}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Enumerated(EnumType.STRING)
	public PerfilEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}
	@Access(AccessType.PROPERTY)
	@OneToMany(targetEntity= Timesheet.class, mappedBy = "collaborator", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	public List<Timesheet> getTimehseets() {
		return timehseets;
	}

	public void setTimehseets(List<Timesheet> timehseets) {
		this.timehseets = timehseets;
	}
	
	@Override
	public String toString() {
		return "Collaborator [Id=" + Id + ", name=" + name + ", password=" + password + ", perfil="
				+ perfil + "]";
	}




}
