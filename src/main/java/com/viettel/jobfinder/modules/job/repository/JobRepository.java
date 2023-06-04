package com.viettel.jobfinder.modules.job.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viettel.jobfinder.modules.employer.Employer;
import com.viettel.jobfinder.modules.job.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

  List<Job> findByEmployer(Employer employer);

  @Query(value = "SELECT * FROM jobs WHERE job_id = :jobId OR (:jobId IS NULL AND user_employer_id = :userEmployerId) ORDER BY job_id ASC LIMIT :limit OFFSET :offset", nativeQuery = true)
  List<Job> filterJobs(@Param("jobId") Long jobId, @Param("userEmployerId") Long userEmployerId,
      @Param("limit") int limit, @Param("offset") int offset);

}
