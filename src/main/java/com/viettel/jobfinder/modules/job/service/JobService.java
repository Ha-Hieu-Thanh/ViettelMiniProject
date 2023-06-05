package com.viettel.jobfinder.modules.job.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.viettel.jobfinder.modules.employer.Employer;
import com.viettel.jobfinder.modules.employer.service.EmployerService;
import com.viettel.jobfinder.modules.job.Job;
import com.viettel.jobfinder.modules.job.dto.CreateJobRequestDto;
import com.viettel.jobfinder.modules.job.dto.EditJobRequestDto;
import com.viettel.jobfinder.modules.job.repository.JobRepository;
import com.viettel.jobfinder.shared.exception.NotFoundException;

@Service
public class JobService {
  @Autowired
  private JobRepository jobRepository;
  @Autowired
  private EmployerService employerService;

  public Job getJobById(long id) {
    return jobRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Job not found"));
  }

  public Job createJob(long userId, CreateJobRequestDto data) {
    Employer employer = employerService.getEmployerInfo(userId);
    Job job = new Job();
    job.setTitle(data.getTitle());
    job.setDescription(data.getDescription());
    job.setLocation(data.getLocation());
    job.setSalary(data.getSalary());
    // neu truyen vao active thi set vao job, neu khong thi de mac dinh la false

    if (Objects.nonNull(data.isActive())) {
      job.setActive(data.isActive());
    } else {
      job.setActive(true);
    }
    job.setEmployer(employer);
    jobRepository.save(job);
    return job;
  }

  public Job editJob(long userId, long jobId, EditJobRequestDto data) {
    Job job = jobRepository.findById(jobId)
        .orElseThrow(() -> new NotFoundException("Job not found"));
    Employer employer = employerService.getEmployerInfo(userId);
    if (!job.getEmployer().equals(employer)) {
      throw new NotFoundException("Job not found || You dont have permission to edit this job");
    }
    if (Objects.nonNull(data.getTitle())) {
      job.setTitle(data.getTitle());
    }
    if (Objects.nonNull(data.getDescription())) {
      job.setDescription(data.getDescription());
    }
    if (Objects.nonNull(data.getLocation())) {
      job.setLocation(data.getLocation());
    }
    if (Objects.nonNull(data.getSalary())) {
      job.setSalary(data.getSalary());
    }
    if (Objects.nonNull(data.isActive())) {
      job.setActive(data.isActive());
    }
    return jobRepository.save(job);
  }

  public void deleteJob(long userId, long jobId) {
    Job job = jobRepository.findById(jobId)
        .orElseThrow(() -> new NotFoundException("Job not found"));
    Employer employer = employerService.getEmployerInfo(userId);
    if (!job.getEmployer().equals(employer)) {
      throw new NotFoundException("Job not found || You dont have permission to delete this job");
    }
    jobRepository.delete(job);
  }

  public List<Job> getJobList() {
    return jobRepository.findAll();
  }

  public List<Job> getJobListByEmployerId(long userEmployerId) {
    Employer employer = employerService.getEmployerInfo(userEmployerId);
    return jobRepository.findByEmployer(employer);
  }

  public Page<Job> filterJobs(Long jobId, Long userEmployerId, String title, String location, Long active,
      Pageable pageable) {
    Long employerId = userEmployerId != null ? employerService.getEmployerInfo(userEmployerId).getId() : null;
    return jobRepository.filterJobs(jobId, employerId, title, location, active, pageable);
  }
}
