package com.viettel.jobfinder.modules.job.controller;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

// import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.jobfinder.modules.job.Job;
import com.viettel.jobfinder.modules.job.dto.CreateJobRequestDto;
import com.viettel.jobfinder.modules.job.dto.EditJobRequestDto;
import com.viettel.jobfinder.modules.job.dto.JobResponseDto;
import com.viettel.jobfinder.modules.job.dto.SearchJobQueryDto;
import com.viettel.jobfinder.modules.job.service.JobService;
import com.viettel.jobfinder.shared.Utils;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.EmployeePermission;
import com.viettel.jobfinder.shared.annotation.EmployerPermission;
import com.viettel.jobfinder.shared.annotation.Public;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

@RestController
@RequestMapping("job")
public class JobController {
  @Autowired
  private JobService jobService;

  @GetMapping
  @Public
  public ResponseEntity<List<JobResponseDto>> filterJobs(
      @RequestParam(name = "jobId", required = false) Long jobId,
      @RequestParam(name = "userEmployerId", required = false) Long userEmployerId,
      @RequestParam(name = "title", required = false) String title,
      @RequestParam(name = "location", required = false) String location,
      @RequestParam(name = "active", required = false) Long active,
      @PageableDefault(size = 20, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    Page<Job> jobs = jobService.filterJobs(jobId, userEmployerId, title, location, active, pageable);
    Function<Job, JobResponseDto> mapJobToJobResponseDto = JobResponseDto::new;
    return Utils.buildPaginationResponse(jobs, mapJobToJobResponseDto);
  }

  @PostMapping
  @EmployerPermission
  public ResponseEntity<JobResponseDto> createJob(@CurrentUser("id") long userId,
      @Valid @RequestBody CreateJobRequestDto data) {
    Job job = jobService.createJob(userId, data);
    JobResponseDto jobResponseDto = new JobResponseDto(job);
    return ResponseEntity.ok(jobResponseDto);
  }

  @PutMapping("/{jobId}")
  @EmployerPermission
  public ResponseEntity<JobResponseDto> editJob(@CurrentUser("id") long userId, @PathVariable("jobId") long jobId,
      @Valid @RequestBody EditJobRequestDto data) {
    Job job = jobService.editJob(userId, jobId, data);
    JobResponseDto jobResponseDto = new JobResponseDto(job);

    return ResponseEntity.ok(jobResponseDto);
  }

  @DeleteMapping("/{jobId}")
  @EmployerPermission
  public ResponseEntity<Object> deleteJob(@CurrentUser("id") long userId, @PathVariable("jobId") long jobId) {
    jobService.deleteJob(userId, jobId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
