package com.viettel.jobfinder.modules.employer.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.viettel.jobfinder.modules.employer.Employer;
import com.viettel.jobfinder.modules.job.Job;
import com.viettel.jobfinder.modules.job.dto.JobResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployerResponseDto {

  private long userId;

  private String username;

  private String fullName;

  private String email;

  private String mobilePhone;

  private String location;

  private String websiteLink;

  private String description;

  private List<JobResponseDto> jobs;

  public EmployerResponseDto(Employer employer) {
    this.userId = employer.getUser().getId();
    this.username = employer.getUser().getUsername();
    this.fullName = employer.getUser().getFullName();
    this.email = employer.getUser().getEmail();
    this.mobilePhone = employer.getUser().getMobilePhone();
    this.location = employer.getUser().getLocation();
    this.websiteLink = employer.getWebsiteLink();
    this.description = employer.getDescription();
    // this.jobs = employer.getJobs();
    this.jobs = employer.getJobs().stream()
        .map(JobResponseDto::new)
        .collect(Collectors.toList());
  }
}
