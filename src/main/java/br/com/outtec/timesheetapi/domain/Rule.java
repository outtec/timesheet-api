package br.com.outtec.timesheetapi.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Rule implements Serializable{
	private static final long serialVersionUID = -242771507735786282L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String initialHour;
	private String finalHour;
	private String rule;
	private Long value;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "collaborator_id")
	private Collaborator collaborator;

	
	public Rule(Long id, String initialHour,String finalHour, String rule, Long value){
		this.id = id;
		this.initialHour = initialHour;
		this.finalHour = finalHour;
		this.rule = rule;
		this.value = value;
	}
	
	public String getInitialHour() {
		return initialHour;
	}

	public void setInitialHour(String initialHour) {
		this.initialHour = initialHour;
	}

	public String getFinalHour() {
		return finalHour;
	}

	public void setFinalHour(String finalHour) {
		this.finalHour = finalHour;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
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
		Rule other = (Rule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
