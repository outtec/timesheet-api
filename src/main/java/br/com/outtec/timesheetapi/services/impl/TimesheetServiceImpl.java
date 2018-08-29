package br.com.outtec.timesheetapi.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.outtec.timesheetapi.domain.Timesheet;
import br.com.outtec.timesheetapi.repositories.TimesheetRepository;
import br.com.outtec.timesheetapi.services.TimesheetService;


@Service
public class TimesheetServiceImpl implements TimesheetService{

	DateTime startDate;
	DateTime endDate;
	DateTime timeInit; // = new DateTime(2018,8,27,21,0);
	DateTime timeEnd; // = new DateTime(2000,8,27,23,59);
	Interval intervalAfter21;
	Period objPeriodAfter21;
	Period totalAfter21 = new Period(0);
	String timeAfter21;
	
	private static final Logger log = LoggerFactory.getLogger(TimesheetServiceImpl.class);

	@Autowired
	private TimesheetRepository repo;

	public Timesheet save(Timesheet obj) {

		DateTime startDateTimeObj = new DateTime(obj.getStartDateTime());
		DateTime endDateTimeObj = new DateTime(obj.getEndDateTime());

		//calcula o total de horas do lancamento.
		Interval interval = new Interval(startDateTimeObj,endDateTimeObj);

		//Formata o lancamento para padrão de hora
		String strTime = interval.toPeriod().getHours()+":"+interval.toPeriod().getMinutes();
		strTime = insereZeroAEsquerda(interval.toPeriod().getHours(),interval.toPeriod().getMinutes());

		obj.setTotalTime(strTime);
		log.info("Persistindo Timesheet: {}", obj);
		return this.repo.save(obj); 
	} 

	public Optional<Timesheet> findByID(Long id){
		log.info("Buscando Timesheet por ID: {}", id);
		return this.repo.findById(id);
	}

	public List<Timesheet> returnTimesheets() {
		List<Timesheet> List = repo.findAll();
		return List;

	}

	public void delete(Long id) {
		this.repo.deleteById(id);
	}

	public  Page<Timesheet> findByCollaboratorId(Long id, PageRequest pageRequest){
		return this.repo.findByCollaboratorId(id, pageRequest);
	};

	public boolean checkExistingTimesheet(Timesheet obj) {
		Boolean existeTimesheet = false;
		List<Timesheet> list = this.repo.findByStartDateTimeAndEndDateTimeAndCollaborator(obj.getStartDateTime(),obj.getEndDateTime(),obj.getCollaborator());
		if (list.isEmpty()){
			return false;
		}
		Timesheet timesheet = list.get(0);
		if(timesheet != null && !obj.equals(timesheet)){
			existeTimesheet = true;
		}
		return existeTimesheet;
	}

	@Override
	public List<Timesheet> findByCollaboratorId(Long id) {
		return this.repo.findByCollaboratorId(id);
	}

	public Page<Timesheet> getByPeriod(Date startDate, Date endDate, PageRequest pageRequest) {
		return this.repo.findByCollaboratorBetween(startDate, endDate,pageRequest);
	}

	public Page<Timesheet> findByCollaboratorIdAndStarDateTime(Long collaboratorId, Date startDateTime, PageRequest pageRequest) {
		return this.repo.findByCollaboratorIdAndStartDateTime(collaboratorId, startDateTime, pageRequest);
	}
	
	public void calculaHoraForaDeHorarioComercial(Long collaborator_id){

		
		List<Timesheet> lancamentos = this.repo.findByCollaboratorId(collaborator_id);
		lancamentos.stream().forEach(lancamento ->{ 
			startDate = new DateTime(lancamento.getStartDateTime());
			Integer ano = startDate.getYear();
			Integer mes =  startDate.getMonthOfYear();
			Integer dia = startDate.getDayOfMonth();
			
			endDate = new DateTime(lancamento.getEndDateTime());
			timeInit = new DateTime(ano,mes,dia,21,0);
			timeEnd = new DateTime(ano,mes,dia,23,59);
			if(startDate.isAfter(timeInit)){
				intervalAfter21 = new Interval(startDate,endDate);
				objPeriodAfter21 = new Period(intervalAfter21);
				totalAfter21 = totalAfter21.plusHours(objPeriodAfter21.getHours()).plusMinutes(objPeriodAfter21.getMinutes());
				//totalAfter21 = totalAfter21.plusMinutes(objPeriodAfter21.getMinutes());		
				String timeAfter21 = insereZeroAEsquerda(objPeriodAfter21.getHours(),objPeriodAfter21.getMinutes());
			}

			if(startDate.isBefore(timeInit) && (endDate.isAfter(timeInit))){
				//Pega do tempo inicial até as 21h
				intervalAfter21 = new Interval(startDate,timeInit);
				objPeriodAfter21 = new Period(intervalAfter21);
				timeAfter21 = insereZeroAEsquerda(objPeriodAfter21.getHours(),objPeriodAfter21.getMinutes());

				//Pega de 21h até o tempo final
				Interval intervalABefore21 = new Interval(timeInit,endDate);
				Period objPeriodBefore21 = new Period(intervalABefore21);
				String timeBefore21 = insereZeroAEsquerda(objPeriodBefore21.getHours(),objPeriodBefore21.getMinutes());
				totalAfter21 = totalAfter21.plusHours(objPeriodBefore21.getHours()).plusMinutes(objPeriodBefore21.getMinutes());
				//totalAfter21 = totalAfter21.plusMinutes(objPeriodBefore21.getMinutes());
		}
		});
		System.out.println("TOTAL "+totalAfter21);
	}
	
	@Override
	public String insereZeroAEsquerda(Integer horas, Integer minutos){
		String horaFormatada = "";
		if (horas< 10){
			horaFormatada = "0"+horas+":";
		}
		if (minutos< 10){
			horaFormatada = horaFormatada +"0"+minutos;
		}else {
			horaFormatada = horaFormatada + minutos;
		}
		return horaFormatada;
	}


}