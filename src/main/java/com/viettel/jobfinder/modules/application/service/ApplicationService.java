package com.viettel.jobfinder.modules.application.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.jobfinder.modules.application.Application;
import com.viettel.jobfinder.modules.application.dto.EditApplicationRequestDto;
import com.viettel.jobfinder.modules.application.repository.ApplicationRepository;
import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.employee.dto.EmployeeResponseDto;
import com.viettel.jobfinder.modules.employee.service.EmployeeService;
import com.viettel.jobfinder.modules.employer.Employer;
import com.viettel.jobfinder.modules.employer.service.EmployerService;
import com.viettel.jobfinder.modules.job.dto.JobResponseDto;
import com.viettel.jobfinder.modules.job.service.JobService;
import com.viettel.jobfinder.modules.user.service.UserService;
import com.viettel.jobfinder.shared.exception.NotFoundException;

@Service
public class ApplicationService {
  @Autowired
  private ApplicationRepository applicationRepository;
  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private EmployerService employerService;
  @Autowired
  private JobService jobService;

  public Application applyJob(long userId, long jobId) {
    Application application = new Application();
    application.setEmployee(employeeService.getEmployeeInfo(userId));
    application.setJob(jobService.getJobById(jobId));
    return applicationRepository.save(application);
  }

  public void deleteApplication(long userId, long jobId) {
    Employee employee = employeeService.getEmployeeInfo(userId);
    Application application = applicationRepository.findByEmployee_IdAndJob_Id(employee.getId(), jobId)
        .orElseThrow(() -> new NotFoundException("You dont have this job"));
    applicationRepository.delete(application);
  }

  public Application getApplicationByEmployee(long userId, long jobId) {
    Employee employee = employeeService.getEmployeeInfo(userId);
    Application application = applicationRepository.findByEmployee_IdAndJob_Id(employee.getId(), jobId)
        .orElseThrow(() -> new NotFoundException("You dont have this job"));
    return application;
  }

  public Application getApplicationByEmployer(long userEmployerId, long userEmployeeId, long jobId) {
    Employer employer = employerService.getEmployerInfo(userEmployerId);
    // check if employer have job
    if (employer.getJobs().stream().noneMatch(job -> job.getId() == jobId)) {
      throw new NotFoundException("You dont have this job");
    }
    return getApplicationByEmployee(userEmployeeId, jobId);
  }

  public List<Application> getJobListByUserId(long userId) {
    Employee employee = employeeService.getEmployeeInfo(userId);
    List<Application> applications = applicationRepository.findByEmployee_Id(employee.getId())
        .orElseThrow(() -> new NotFoundException("You dont have any application"));
    return applications;
  }

  public List<Application> getEmployeeListByJobId(long userId, long jobId) {
    Employer employer = employerService.getEmployerInfo(userId);
    // check if employer have job
    if (employer.getJobs().stream().noneMatch(job -> job.getId() == jobId)) {
      throw new NotFoundException("You dont have this job");
    }
    List<Application> applications = applicationRepository.findByJob_Id(jobId)
        .orElseThrow(() -> new NotFoundException("This job dont have any application"));

    return applications;
  }

  public Application editApplication(long userEmployerId, long userEmployeeId, long jobId,
      EditApplicationRequestDto data) {
    Employee employee = employeeService.getEmployeeInfo(userEmployeeId);
    Employer employer = employerService.getEmployerInfo(userEmployerId);
    // check if employer have job
    if (employer.getJobs().stream().noneMatch(job -> job.getId() == jobId)) {
      throw new NotFoundException("You dont have this job");
    }

    Application application = applicationRepository.findByEmployee_IdAndJob_Id(employee.getId(), jobId)
        .orElseThrow(() -> new NotFoundException("This employee does not apply to this job"));

    if (Objects.nonNull(data.isAccepted())) {
      application.setAccepted(data.isAccepted());
    }

    if (Objects.nonNull(data.getMessage())) {
      application.setMessage(data.getMessage());
    }

    return applicationRepository.save(application);
  }

}
