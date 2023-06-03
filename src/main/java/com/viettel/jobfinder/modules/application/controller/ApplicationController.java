package com.viettel.jobfinder.modules.application.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.jobfinder.modules.application.Application;
import com.viettel.jobfinder.modules.application.dto.ApplicationResponseDto;
import com.viettel.jobfinder.modules.application.dto.EditApplicationRequestDto;
import com.viettel.jobfinder.modules.application.service.ApplicationService;
import com.viettel.jobfinder.modules.employee.dto.EmployeeResponseDto;
import com.viettel.jobfinder.modules.job.dto.JobResponseDto;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.EmployeePermission;
import com.viettel.jobfinder.shared.annotation.EmployerPermission;

@RestController
@RequestMapping("application")
public class ApplicationController {
  @Autowired
  private ApplicationService applicationService;

  // apply job, only for Employee
  @PostMapping("/employee/{jobId}")
  @EmployeePermission
  public ResponseEntity<ApplicationResponseDto> createApplication(@CurrentUser("id") long userId,
      @PathVariable("jobId") long jobId) {
    Application application = applicationService.applyJob(userId, jobId);
    return ResponseEntity.ok(new ApplicationResponseDto(application));
  }

  // cancel application, only for Employee
  @DeleteMapping("/employee/{jobId}")
  @EmployeePermission
  public ResponseEntity<Object> cancelApplication(@CurrentUser("id") long userId,
      @PathVariable("jobId") long jobId) {
    applicationService.deleteApplication(userId, jobId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // get application
  // @GetMapping("/employee/{jobId}")
  // @EmployeePermission
  // public ResponseEntity<ApplicationResponseDto>
  // getApplicationByEmployee(@CurrentUser("id") long userId,
  // @PathVariable("jobId") long jobId) {
  // Application application = applicationService.getApplicationByEmployee(userId,
  // jobId);
  // return ResponseEntity.ok(new ApplicationResponseDto(application));
  // }

  // get all applied job for employee
  @GetMapping("/employee")
  @EmployeePermission
  public ResponseEntity<List<ApplicationResponseDto>> getAppliedJobs(@CurrentUser("id") long userId) {
    List<Application> applications = applicationService.getJobListByUserId(userId);
    List<ApplicationResponseDto> applicationsResponse = applications.stream().map(ApplicationResponseDto::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(applicationsResponse);
  }

  // get all applicants
  @GetMapping("/employer/{jobId}")
  @EmployerPermission
  public ResponseEntity<List<ApplicationResponseDto>> getApplicants(@CurrentUser("id") long userId,
      @PathVariable("jobId") long jobId) {
    List<Application> applications = applicationService.getEmployeeListByJobId(userId, jobId);
    List<ApplicationResponseDto> applicationsResponse = applications.stream()
        .map(ApplicationResponseDto::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(applicationsResponse);
  }

  @PutMapping("/employer/{jobId}/{userEmployeeId}")
  @EmployerPermission
  public ResponseEntity<ApplicationResponseDto> updateApplicationStatus(@CurrentUser("id") long userId,
      @PathVariable("userEmployeeId") long userEmployeeId,
      @PathVariable("jobId") long jobId, EditApplicationRequestDto data) {
    Application updatedApplication = applicationService.editApplication(userId, userEmployeeId, jobId, data);
    return new ResponseEntity<>(new ApplicationResponseDto(updatedApplication), HttpStatus.NO_CONTENT);
  }

  // @GetMapping("/employer/{userEmployeeId}/{jobId}")
  // @EmployerPermission
  // public ResponseEntity<ApplicationResponseDto>
  // getApplicationByEmployer(@CurrentUser("id") long userEmployerId,
  // @PathVariable("userEmployeeId") long userEmployeeId, @PathVariable("jobId")
  // long jobId) {
  // Application application =
  // applicationService.getApplicationByEmployer(userEmployerId, userEmployeeId,
  // jobId);
  // return new ResponseEntity<>(new ApplicationResponseDto(application),
  // HttpStatus.OK);
  // }

}
