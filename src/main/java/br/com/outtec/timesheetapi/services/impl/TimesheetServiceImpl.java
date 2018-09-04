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



	//Novas Variaveis
	DateTime iniForaDeHora; 
	DateTime fimForaDeHora;
	
	Interval intevalForaDeHoraComercial;
	Interval intevalDentroDeHoraComercial;
	
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
			
			Integer ano = startDate.getYear();
			Integer mes =  startDate.getMonthOfYear();
			Integer dia = startDate.getDayOfMonth();
			
			
			//inicia o período para calculo dentro do horário de 21:00 até às 23:59
			iniForaDeHora = new DateTime(ano,mes,dia,21,0);
			fimForaDeHora = new DateTime(ano,mes,dia,23,59);
			
			//Se o horário a hora de Inicio do lancamento for após as 21 então considera todo o lancamento como fora do horário
			if(startDate.isAfter(iniForaDeHora) && (endDate.isBefore(fimForaDeHora))){
				periodForaDeHoraComercial = new Period(intevalForaDeHoraComercial = new Interval(startDate,endDate));
				totalForaDeHoraComercial = totalForaDeHoraComercial.plusHours(periodForaDeHoraComercial.getHours()).plusMinutes(periodForaDeHoraComercial.getMinutes());
				horasAposHorario = insereZeroAEsquerda(totalForaDeHoraComercial.getHours(),totalForaDeHoraComercial.getMinutes());
			}
			
			//Se o horario de inicio do lançamento for anterior as 21h mas o horário de saída passa das 21h então calcula hora normal e hora não util.
			if(startDate.isBefore(iniForaDeHora) && (endDate.isAfter(iniForaDeHora))){

				//Pega do tempo inicial até o inicio do período fora do horario Ex.:21h essas são as horas normais
				periodDentroDeHoraComercial = new Period(intevalDentroDeHoraComercial = new Interval(startDate,iniForaDeHora));
				totalDentroDeHoraComercial = totalDentroDeHoraComercial.plusHours(periodDentroDeHoraComercial.getHours()).plusMinutes(periodDentroDeHoraComercial.getMinutes());
				horasNormais = insereZeroAEsquerda(periodDentroDeHoraComercial.getHours(),periodDentroDeHoraComercial.getMinutes());
 
				//Pega do inicio do periodo fora do horário comercial até o tempo final
				periodForaDeHoraComercial = new Period(intevalForaDeHoraComercial = new Interval(iniForaDeHora,endDate));
				totalForaDeHoraComercial = totalForaDeHoraComercial.plusHours(periodForaDeHoraComercial.getHours()).plusMinutes(periodForaDeHoraComercial.getMinutes());
				horasAposHorario = insereZeroAEsquerda(totalForaDeHoraComercial.getHours(),totalForaDeHoraComercial.getMinutes());
				
			}
 
		});
		System.out.println(horasNormais); 
		System.out.println(horasAposHorario);	

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