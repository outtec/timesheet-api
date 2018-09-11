package br.com.outtec.timesheetapi.services.impl;

import java.util.Calendar;
import java.util.Date;
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


	public void getTimesheetsPorPeriodo(Long collaborator_id, DateTime start,DateTime end){
		List<Timesheet> lancamentos = this.repo.findByCollaboratorId(collaborator_id);
		lancamentos.stream().forEach(lancamento ->{ 
			int i = 0;
			Calendar cal = Calendar.getInstance();
			cal.setTime(lancamento.getStartDateTime());

			Integer dia = cal.get(Calendar.YEAR);
			Integer mes = cal.get(Calendar.MONTH)+1;
			Integer ano = cal.get(Calendar.DAY_OF_MONTH);

			DateTime dtLancamento = new DateTime(dia, mes, ano, 0, 0);
			Days inicioPeriodo = Days.daysBetween(start,dtLancamento);
			Days finalPeriodo = Days.daysBetween(end,dtLancamento);

			if((inicioPeriodo.getDays()>=0) && (finalPeriodo.getDays()<=0)){
				if(lancamento != null) {
				calculaHoraForaDeHorarioComercial(lancamento);
				}
			}
		});

	}


	
	public void calculaHoraForaDeHorarioComercial(Timesheet lancamento){
			System.err.println(lancamento);
			Interval totalHoraNaoUtil = null;
			Interval totalHoraUtil = null;

			Calendar cal = Calendar.getInstance();
			cal.setTime(lancamento.getStartDateTime());

			Integer anoCorrente = cal.get(Calendar.YEAR);
			Integer mesCorrente = cal.get(Calendar.MONTH)+1;
			Integer diaCorrente = cal.get(Calendar.DAY_OF_MONTH);

			DateTime inicioDia = new DateTime(anoCorrente, mesCorrente, diaCorrente, 0, 0);
			DateTime inicioUtil = new DateTime(anoCorrente, mesCorrente, diaCorrente, 6, 0);
			DateTime inicioNoite = new DateTime(anoCorrente,mesCorrente, diaCorrente, 21, 0);
			DateTime finalNoite = new DateTime(anoCorrente, mesCorrente,diaCorrente, 23, 59);

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
			System.out.println("totalHoraNaoUtil " + formataHora(totalHoraNaoUtil.toPeriod().getHours(), totalHoraNaoUtil.toPeriod().getMinutes())); 
			System.out.println("horaUteis "+ formataHora(totalHoraUtil.toPeriod().getHours(), totalHoraUtil.toPeriod().getMinutes()));	
		
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