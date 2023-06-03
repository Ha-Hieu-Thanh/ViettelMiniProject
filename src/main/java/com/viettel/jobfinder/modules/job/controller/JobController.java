package com.viettel.jobfinder.modules.job.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.jobfinder.modules.job.Job;
import com.viettel.jobfinder.modules.job.dto.CreateJobRequestDto;
import com.viettel.jobfinder.modules.job.dto.EditJobRequestDto;
import com.viettel.jobfinder.modules.job.dto.JobResponseDto;
import com.viettel.jobfinder.modules.job.service.JobService;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.EmployeePermission;
import com.viettel.jobfinder.shared.annotation.EmployerPermission;
import com.viettel.jobfinder.shared.annotation.Public;

@RestController
@RequestMapping("job")
public class JobController {
  @Autowired
  private JobService jobService;

  @GetMapping
  @Public
  // @EmployeePermission
  public ResponseEntity<List<JobResponseDto>> getJobs() {
    System.out.println("Hello");
    List<Job> jobs = jobService.getJobList();
    List<JobResponseDto> jobResponseDtos = jobs.stream()
        .map(JobResponseDto::new)
        .collect(Collectors.toList());

    return ResponseEntity.ok(jobResponseDtos);
  }

  @GetMapping("{userEmployerId}")
  @Public
  public ResponseEntity<List<JobResponseDto>> getJobsByEmployerId(@PathVariable("userEmployerId") long userEmployerId) {
    List<Job> jobs = jobService.getJobListByEmployerId(userEmployerId);
    List<JobResponseDto> jobResponseDtos = jobs.stream()
        .map(JobResponseDto::new)
        .collect(Collectors.toList());

    return ResponseEntity.ok(jobResponseDtos);
  }

  @GetMapping("{jobId}")
  @Public
  // @EmployeePermission
  public ResponseEntity<JobResponseDto> getJobById(@PathVariable("jobId") long jobId) {
    System.out.println("HELO");
    Job job = jobService.getJobById(jobId);
    JobResponseDto jobResponseDto = new JobResponseDto(job);

    return ResponseEntity.ok(jobResponseDto);
  }

  @PostMapping
  @EmployerPermission
  public ResponseEntity<JobResponseDto> createJob(@CurrentUser("id") long userId,
      @Valid @RequestBody CreateJobRequestDto data) {
    Job job = jobService.createJob(userId, data);
    JobResponseDto jobResponseDto = new JobResponseDto(job);
    return ResponseEntity.ok(jobResponseDto);
  }

  @PutMapping("{jobId}")
  @EmployerPermission
  public ResponseEntity<JobResponseDto> editJob(@CurrentUser("id") long userId, @PathVariable("jobId") long jobId,
      @Valid @RequestBody EditJobRequestDto data) {
    Job job = jobService.editJob(userId, jobId, data);
    JobResponseDto jobResponseDto = new JobResponseDto(job);

    return ResponseEntity.ok(jobResponseDto);
  }

  @DeleteMapping("{jobId}")
  @EmployerPermission
  public ResponseEntity<Object> deleteJob(@CurrentUser("id") long userId, @PathVariable("jobId") long jobId) {
    jobService.deleteJob(userId, jobId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
