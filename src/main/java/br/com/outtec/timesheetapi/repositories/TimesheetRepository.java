package br.com.outtec.timesheetapi.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.outtec.timesheetapi.domain.Collaborator;
import br.com.outtec.timesheetapi.domain.Timesheet;

@Transactional
@NamedQueries({
	@NamedQuery(name = "TimesheetRepository.findByName", 
			query = "SELECT ts FROM Timesheet ts WHERE ts.collaborator.id = :collaboratorId") })
public interface TimesheetRepository extends JpaRepository<Timesheet,Long>  {



	List<Timesheet> findByCollaboratorId(@Param("collaboratorId") Long collaboratorId);

	Optional<Timesheet> findByStartDateTimeAndEndDateTimeAndCollaborator(Date endDateTime,Date startDateTime,Collaborator collaborator);
	Optional<Timesheet> findByIdAndStartDateTimeAndEndDateTime(Long Id, Date endDateTime, Date startDateTime);

}
