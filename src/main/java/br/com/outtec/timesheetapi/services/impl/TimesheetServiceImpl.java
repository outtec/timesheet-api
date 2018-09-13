package br.com.outtec.timesheetapi.services.impl;

import static org.hamcrest.CoreMatchers.any;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
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


	private static final Logger log = LoggerFactory.getLogger(TimesheetServiceImpl.class);

	@Autowired
	private TimesheetRepository repo;
	
	/**
	 * 
	 * @param collaborator_id
	 * @param dataInicioPeriodo
	 * @param dataFimPeriodo
	 * @return
	 */
	public List<Timesheet> getTimesheetsByPeriod(Long collaborator_id, Date dataInicioPeriodo, Date dataFimPeriodo){
		log.info("Buscando Timesheets por collaborador: {}", collaborator_id );
		return repo.findByStartDateTimeBetween(dataInicioPeriodo, dataFimPeriodo);
	}
	
	/**
	 * Retorna uma lista de timesheets por periodo.
	 */
	public List<Timesheet> getTimesheetsPorPeriodo(Long collaborator_id, DateTime dataInicioPeriodo,DateTime dataFimPeriodo){
		List<Timesheet> lancamentosPorPeriodo = new ArrayList<Timesheet>(); 
		List<Timesheet> lancamentos = this.repo.findByCollaboratorId(collaborator_id);
		for (Iterator<Timesheet> i = lancamentos.iterator(); i.hasNext();) {
			Timesheet timesheet = (Timesheet) i.next();
			DateTime dataLancamento = convertToDateTimeAndSetPeriod(convertToCalendarInstance(timesheet.getStartDateTime()),0,0);
			Days inicioPeriodo = Days.daysBetween(dataInicioPeriodo,dataLancamento);
			Days finalPeriodo = Days.daysBetween(dataFimPeriodo,dataLancamento);
			if((inicioPeriodo.getDays()>=0) && (finalPeriodo.getDays()<=0)){
				lancamentosPorPeriodo.add(timesheet);
			}
		}
		return lancamentosPorPeriodo;
	}

	private DateTime convertToDateTimeAndSetPeriod(Calendar objCalendar,Integer inicioPeriodo, Integer finalPeiodo) {
		return new DateTime(objCalendar.get(Calendar.YEAR), objCalendar.get(Calendar.MONTH)+1, objCalendar.get(Calendar.DAY_OF_MONTH), inicioPeriodo,finalPeiodo);
	}

	private Calendar convertToCalendarInstance(Date data){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		return cal;
	}

	public void calculaHoraForaDeHorarioComercial(Timesheet lancamento){
		Interval totalHoraNaoUtil = null;
		Interval totalHoraUtil = null;

		DateTime inicioDia = convertToDateTimeAndSetPeriod(convertToCalendarInstance(lancamento.getStartDateTime()),0,0);
		DateTime inicioUtil = convertToDateTimeAndSetPeriod(convertToCalendarInstance(lancamento.getStartDateTime()),6,0);
		DateTime inicioNoite = convertToDateTimeAndSetPeriod(convertToCalendarInstance(lancamento.getStartDateTime()),21,0);
		DateTime finalNoite = convertToDateTimeAndSetPeriod(convertToCalendarInstance(lancamento.getStartDateTime()),23,59);
		DateTime startDT = new DateTime(lancamento.getStartDateTime());
		DateTime endDT = new DateTime(lancamento.getEndDateTime());

		Interval iManha = new Interval(inicioDia.getMillis(), inicioUtil.getMillis());
		Interval iUtil = new Interval(inicioUtil.getMillis(), inicioNoite.getMillis());
		Interval iNoite = new Interval(inicioNoite.getMillis(), finalNoite.getMillis());
		Interval i = new Interval(startDT.getMillis(), endDT.getMillis());

		if (iManha.overlaps(i)) {
			totalHoraNaoUtil = iManha.overlap(i);
		}
		if (iUtil.overlaps(i)) {
			totalHoraUtil = iUtil.overlap(i);
		}
		if (iNoite.overlaps(i)) {
			totalHoraNaoUtil = iNoite.overlap(i);
		}

		try {

			lancamento.setExtraTime(formataHora(totalHoraNaoUtil.toPeriod().getHours(), totalHoraNaoUtil.toPeriod().getMinutes()));
			lancamento.setNormalTime(formataHora(totalHoraUtil.toPeriod().getHours(), totalHoraUtil.toPeriod().getMinutes()));
			System.out.println("totalHoraNaoUtil " + formataHora(totalHoraNaoUtil.toPeriod().getHours(), totalHoraNaoUtil.toPeriod().getMinutes())); 
			System.out.println("horaUteis "+ formataHora(totalHoraUtil.toPeriod().getHours(), totalHoraUtil.toPeriod().getMinutes()));		

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Timesheet save(Timesheet obj) {

		DateTime startDateTimeObj = new DateTime(obj.getStartDateTime());
		DateTime endDateTimeObj = new DateTime(obj.getEndDateTime());

		//calcula o total de horas do lancamento.
		Interval interval = new Interval(startDateTimeObj,endDateTimeObj);

		//Formata o lancamento para padrão de hora
		String strTime = interval.toPeriod().getHours()+":"+interval.toPeriod().getMinutes();
		strTime = formataHora(interval.toPeriod().getHours(),interval.toPeriod().getMinutes());
		obj.setTotalTime(strTime);

		//Calcula hora extra
		if(obj.getEndDateTime() != null) {
			calculaHoraForaDeHorarioComercial(obj);
		}
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


	public String formataHora(Integer horas, Integer minutos){
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