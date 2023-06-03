package com.viettel.jobfinder.modules.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viettel.jobfinder.modules.application.Application;
import com.viettel.jobfinder.modules.employee.Employee;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

  public Optional<Application> findByEmployee_IdAndJob_Id(long employee_Id, long job_Id);

  public Optional<List<Application>> findByJob_Id(long jobId);

  public Optional<List<Application>> findByEmployee_Id(long employee_Id);
}
