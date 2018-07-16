package br.com.outtec.timesheetapi.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.domain.Timesheet;

@Transactional

@NamedQueries({
	@NamedQuery(name = "TimesheetRepository.findByCollaboratorId", 
			query = "SELECT ts FROM Timesheet ts WHERE ts.collaborator.id = :collaboratorId")	
	})

public interface TimesheetRepository extends JpaRepository<Timesheet,Long>  {

	List<Timesheet> findByCollaboratorId(@Param("collaboratorId") Long collabodatorId);
	
	Page<Timesheet> findByCollaboratorId(@Param("collaboratorId") Long collabodatorId ,Pageable pegeable);
	
	Optional<Timesheet> findByStartDateTimeAndEndDateTime(Date startDateTime, Date endDateTime);
	
	Optional<Timesheet> findById(Long Id);
	
	List<Timesheet> findByStartDateTimeAndEndDateTimeAndCollaborator(Date startDateTime,Date endDateTime,Collaborator collaborator);
	
	Page<Timesheet> findByCollaboratorBetween(Date startDateTime,Date endDateTime, Pageable pegeable);
	
	Page<Timesheet> findByCollaboratorIdAndStartDateTime(Long collaboratorId, Date startDateTime, Pageable pegeable);
	
	List<Timesheet> findByCollaboratorIdAndStartDateTime(Long collaboratorId, Date startDateTime);
	
	List<Timesheet> findByStartDateTime(Date startDateTime);
}
