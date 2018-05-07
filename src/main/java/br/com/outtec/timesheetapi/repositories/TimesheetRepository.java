package br.com.outtec.timesheetapi.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import br.com.outtec.timesheetapi.domain.Timesheet;


public interface TimesheetRepository extends JpaRepository<Timesheet,Long>  {
	@Transactional(readOnly = true) 
	Optional<Timesheet> findById(Long id);
}
