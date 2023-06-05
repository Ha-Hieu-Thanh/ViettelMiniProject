package com.viettel.jobfinder.modules.job.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viettel.jobfinder.modules.employer.Employer;
import com.viettel.jobfinder.modules.job.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

  List<Job> findByEmployer(Employer employer);

  @Query(value = "SELECT * FROM job WHERE (:jobId IS NULL OR id = :jobId) AND (:employerId IS NULL OR employer_id = :employerId) AND (:title IS NULL OR title LIKE CONCAT('%', :title, '%')) AND (:location IS NULL OR location LIKE CONCAT('%', :location, '%')) AND (:active IS NULL OR active = :active)", nativeQuery = true)
  Page<Job> filterJobs(Long jobId, Long employerId, String title, String location, Long active, Pageable pageable);

}
