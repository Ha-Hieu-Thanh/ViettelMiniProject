package com.viettel.jobfinder.modules.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viettel.jobfinder.modules.application.Application;
import com.viettel.jobfinder.modules.employee.Employee;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

  public Optional<Application> findByEmployee_IdAndJob_Id(long employee_Id, long job_Id);

  public Optional<List<Application>> findByJob_Id(long jobId);

  public Optional<List<Application>> findByEmployee_Id(long employee_Id);

  @Query(value = "SELECT * FROM application WHERE (:jobId IS NULL OR job_id = :jobId) AND (:accepted IS NULL OR accepted = :accepted)", nativeQuery = true)
  public List<Application> filterApplication(Long jobId, Long accepted);
}
