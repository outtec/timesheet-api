package br.com.outtec.timesheetapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.outtec.timesheetapi.domain.Timesheet;


public interface TimesheetRepository extends JpaRepository<Timesheet,Long>  {

}
