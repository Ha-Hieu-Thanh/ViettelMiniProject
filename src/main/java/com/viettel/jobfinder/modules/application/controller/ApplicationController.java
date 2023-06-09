package com.viettel.jobfinder.modules.application.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.viettel.jobfinder.shared.sendGrid.SendGridMailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("application")
@Tag(name = "Application")
@Order(8)
public class ApplicationController {
  @Autowired
  private ApplicationService applicationService;

  @Autowired
  private SendGridMailService sendGridMailService;

  @Operation(summary = "Apply job (only for EMPLOYEE)")
  @PostMapping("/employee/{jobId}")
  @EmployeePermission
  public ResponseEntity<ApplicationResponseDto> createApplication(@CurrentUser("id") long userId,
      @PathVariable("jobId") long jobId) {
    Application application = applicationService.applyJob(userId, jobId);
    return ResponseEntity.ok(new ApplicationResponseDto(application));
  }

  // cancel application, only for Employee
  @Operation(summary = "Cancel application (only for Employee)")
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
  @Operation(summary = "Get all applied job (only for Employee)")
  @GetMapping("/employee")
  @EmployeePermission
  public ResponseEntity<List<ApplicationResponseDto>> getAppliedJobs(@CurrentUser("id") long userId) {
    List<Application> applications = applicationService.getJobListByUserId(userId);
    List<ApplicationResponseDto> applicationsResponse = applications.stream().map(ApplicationResponseDto::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(applicationsResponse);
  }

  @Operation(summary = "Get all applicants (only for Employer)")
  @GetMapping("/employer")
  @EmployerPermission
  public ResponseEntity<List<ApplicationResponseDto>> getApplicants(@CurrentUser("id") long userId,
      @RequestParam(name = "jobId", required = false) Long jobId,
      @RequestParam(name = "accepted", required = false) Long accepted) {
    List<Application> applications = applicationService.filterApplication(userId, jobId, accepted);
    List<ApplicationResponseDto> applicationsResponse = applications.stream()
        .map(ApplicationResponseDto::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(applicationsResponse);
  }

  @Operation(summary = "Approve application (only for Employer)")
  @PutMapping("/employer")
  @EmployerPermission
  public ResponseEntity<ApplicationResponseDto> updateApplicationStatus(@CurrentUser("id") long userId,
      @RequestParam("userEmployeeId") long userEmployeeId,
      @RequestParam("jobId") long jobId, @Valid @RequestBody EditApplicationRequestDto data) {
    Application updatedApplication = applicationService.editApplication(userId, userEmployeeId, jobId, data);
    return new ResponseEntity<>(new ApplicationResponseDto(updatedApplication), HttpStatus.OK);
  }

}
