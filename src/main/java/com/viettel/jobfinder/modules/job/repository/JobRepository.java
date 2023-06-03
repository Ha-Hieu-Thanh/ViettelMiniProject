package com.viettel.jobfinder.modules.job.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viettel.jobfinder.modules.employer.Employer;
import com.viettel.jobfinder.modules.job.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

  List<Job> findByEmployer(Employer employer);

}
