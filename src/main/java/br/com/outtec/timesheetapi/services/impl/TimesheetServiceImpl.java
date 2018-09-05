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

	DateTime iniForaDeHora; 
	DateTime fimForaDeHora;

	Period periodForaDeHoraComercial;
	Period periodDentroDeHoraComercial;

	Period totalForaDeHoraComercial = new Period(0);
	Period totalDentroDeHoraComercial = new Period(0);

	String horasNormais;
	String horasAposHorario;

	private static final Logger log = LoggerFactory.getLogger(TimesheetServiceImpl.class);

	@Autowired
	private TimesheetRepository repo;

	public void calculaHoraForaDeHorarioComercial(Long collaborator_id){
		List<Timesheet> lancamentos = this.repo.findByCollaboratorId(collaborator_id);
		lancamentos.stream().forEach(lancamento ->{ 

			DateTime startDate = new DateTime(lancamento.getStartDateTime());
			DateTime endDate = new DateTime(lancamento.getEndDateTime()); 

			iniForaDeHora = new DateTime(startDate.getYear(),startDate.getMonthOfYear(),startDate.getDayOfMonth(),06,00);
			fimForaDeHora = new DateTime(startDate.getYear(),startDate.getMonthOfYear(),startDate.getDayOfMonth(),20,59);
			//System.err.println(startDate.getDayOfWeek());
			
					
			//VERIFICA SE O LANCAMENTO ESTÁ ENTRE OS DOIS PERIODOS DE HORA NÃO UTEIS
			if(startDate.isAfter(iniForaDeHora) && (endDate.isBefore(fimForaDeHora))) {
				periodDentroDeHoraComercial = calculaPeriodoDeHora(startDate,endDate);
				totalDentroDeHoraComercial = totalHoraUtil();
				horasNormais = insereZeroAEsquerda(totalDentroDeHoraComercial.getHours(),totalDentroDeHoraComercial.getMinutes());
			}else {

				//INICIA O PERIODO PARA CALCULO ENTRE 21:00 E 23:59
				iniForaDeHora = new DateTime(startDate.getYear(),startDate.getMonthOfYear(),startDate.getDayOfMonth(),21,0);
				fimForaDeHora = new DateTime(startDate.getYear(),startDate.getMonthOfYear(),startDate.getDayOfMonth(),23,59);

				System.err.println(startDate);			

				//Se a hora de Inicio do lancamento for após as 21 então considera todo o lancamento como fora do horário
				if( ((startDate.equals(iniForaDeHora)) || (startDate.isAfter(iniForaDeHora))) && (endDate.isBefore(fimForaDeHora))){
					periodForaDeHoraComercial = calculaPeriodoDeHora(startDate,endDate);
					totalForaDeHoraComercial = totalHoraNaoUtil();
					horasAposHorario = insereZeroAEsquerda(totalForaDeHoraComercial.getHours(),totalForaDeHoraComercial.getMinutes());
				}

				//Se o horario de inicio do lançamento for anterior as 21h mas o horário de saída passa das 21h então calcula hora normal e hora não util.
				if( (startDate.isBefore(iniForaDeHora)) && (endDate.isAfter(iniForaDeHora))){

					//Pega do tempo inicial até o inicio do período fora do horario Ex.:21h essas são as horas normais
					periodDentroDeHoraComercial = calculaPeriodoDeHora(startDate,iniForaDeHora);
					totalDentroDeHoraComercial = totalHoraUtil();
					horasNormais = insereZeroAEsquerda(periodDentroDeHoraComercial.getHours(),periodDentroDeHoraComercial.getMinutes());

					//Pega do inicio do periodo fora do horário comercial até o tempo final
					periodForaDeHoraComercial = calculaPeriodoDeHora(iniForaDeHora,endDate);
					totalForaDeHoraComercial = totalHoraNaoUtil();
					horasAposHorario = insereZeroAEsquerda(totalForaDeHoraComercial.getHours(),totalForaDeHoraComercial.getMinutes());
				}

				//INICIA CALCULO ENTRE 00:00 até 05:59
				fimForaDeHora = new DateTime(startDate.getYear(),startDate.getMonthOfYear(),startDate.getDayOfMonth(),5,59);
				iniForaDeHora = new DateTime(startDate.getYear(),startDate.getMonthOfYear(),startDate.getDayOfMonth(),0,0);

				if(startDate.isBefore(fimForaDeHora) && ((endDate.equals(fimForaDeHora)) || endDate.isBefore(fimForaDeHora))) {
					periodForaDeHoraComercial = calculaPeriodoDeHora(startDate,endDate);
					totalForaDeHoraComercial = totalHoraNaoUtil();
					horasAposHorario = insereZeroAEsquerda(totalForaDeHoraComercial.getHours(),totalForaDeHoraComercial.getMinutes());
				}

				if(startDate.isBefore(fimForaDeHora) && (endDate.isAfter(fimForaDeHora))) {
					periodForaDeHoraComercial = calculaPeriodoDeHora(startDate,fimForaDeHora);
					totalForaDeHoraComercial = totalHoraNaoUtil();
					horasAposHorario = insereZeroAEsquerda(periodForaDeHoraComercial.getHours(),periodForaDeHoraComercial.getMinutes());

					//Pega do inicio do periodo fora do horário comercial até o tempo final
					periodDentroDeHoraComercial = calculaPeriodoDeHora(fimForaDeHora,endDate);
					totalDentroDeHoraComercial = totalHoraUtil();
					horasNormais = insereZeroAEsquerda(totalDentroDeHoraComercial.getHours(),totalDentroDeHoraComercial.getMinutes());
				}
			}

		});


		System.out.println("_______________TOTAL REAL FINAL____________________");
		System.out.println("HORAS UTIL " + horasNormais); 
		System.out.println("HORAS Ñ UTIL " + horasAposHorario);	
		System.out.println("___________________________________");	} 

	private Period totalHoraUtil() {
		return totalDentroDeHoraComercial.plus(periodDentroDeHoraComercial);
	}

	private Period totalHoraNaoUtil() {
		return totalForaDeHoraComercial.plus(periodForaDeHoraComercial);
	}

	public Period calculaPeriodoDeHora(DateTime inicioPeriodo,DateTime finalPeriodo) {
		return new Period(new Interval(inicioPeriodo,finalPeriodo));
	}

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