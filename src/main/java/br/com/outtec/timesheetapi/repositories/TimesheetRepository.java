package br.com.outtec.timesheetapi.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.outtec.timesheetapi.domain.Timesheet;

@Transactional
public interface TimesheetRepository extends JpaRepository<Timesheet,Long>  {

		
	Optional<Timesheet> findByStartDateTimeAndEndDateTimeAndCollaborator(Date endDateTime,Date startDateTime,String collaborator);
	
	Timesheet findByCollaborator(String collaborator);
	
	Optional<Timesheet> findByIdAndStartDateTimeAndEndDateTime(Long Id, Date endDateTime, Date startDateTime);

	}
