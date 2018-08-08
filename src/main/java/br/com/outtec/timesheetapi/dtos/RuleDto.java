package br.com.outtec.timesheetapi.dtos;

import java.io.Serializable;

public class RuleDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id ;
	private String initialHour;
	private String finalHour;
	private String rule;
	private Long value;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	
}
