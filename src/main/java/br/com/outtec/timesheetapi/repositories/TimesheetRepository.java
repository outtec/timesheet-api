package br.com.outtec.timesheetapi.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.outtec.timesheetapi.domain.Timesheet;

@Transactional
public interface TimesheetRepository extends JpaRepository<Timesheet,Long>  {

	Timesheet findBystartDateTime(Date startDateTime);
	Timesheet findByendDateTime(Date endDateTime);
	Optional<Timesheet> findByStartDateTimeAndEndDateTimeAndColaborador(Date endDateTime,Date startDateTime,String colaborador);
	
}
