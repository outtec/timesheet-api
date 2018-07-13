package br.com.outtec.timesheetapi.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import br.com.outtec.timesheetapi.enums.Perfil;

@Entity
public class Collaborator implements Serializable {
	
	private static final long serialVersionUID = -242771507735786282L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String password;
	private Perfil perfil;
	
	@OneToMany(targetEntity= Timesheet.class, mappedBy = "collaborator", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	private List<Timesheet> timesheets;

	public Collaborator() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	@Column(name = "perfil", nullable = false)
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Timesheet> gettimesheets() {
		return timesheets;
	}

	public void settimesheets(List<Timesheet> timesheets) {
		this.timesheets = timesheets;
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
		Collaborator other = (Collaborator) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Collaborator [Id=" + id + ", name=" + name + ", password=" + password + ", perfil="
				+ perfil + "]";
	}




}
