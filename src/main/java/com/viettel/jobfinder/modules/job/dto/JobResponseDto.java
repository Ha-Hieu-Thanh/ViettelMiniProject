package com.viettel.jobfinder.modules.job.dto;

import com.viettel.jobfinder.modules.job.Job;

import lombok.Data;

@Data
public class JobResponseDto {
  private long jobId;

  private String companyName; // from User
  private String email; // from User
  private String mobilePhone; // from User
  private String companyAddress; // from User

  // from Employer
  private String websiteLink;
  private String companyDescription;

  // from Job
  private String title;
  private String jobDescription;
  private String location;
  private String salary;
  private boolean active;

  public JobResponseDto(Job job) {
    this.jobId = job.getId();
    this.companyName = job.getEmployer().getUser().getFullName();
    this.email = job.getEmployer().getUser().getEmail();
    this.mobilePhone = job.getEmployer().getUser().getMobilePhone();
    this.companyAddress = job.getEmployer().getUser().getLocation();

    this.websiteLink = job.getEmployer().getWebsiteLink();
    this.companyDescription = job.getEmployer().getDescription();

    this.title = job.getTitle();
    this.jobDescription = job.getDescription();
    this.location = job.getLocation();
    this.salary = job.getSalary();
    this.active = job.isActive();

  }
}
