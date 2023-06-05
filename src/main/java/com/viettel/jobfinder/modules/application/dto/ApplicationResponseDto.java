package com.viettel.jobfinder.modules.application.dto;

import com.viettel.jobfinder.modules.application.Application;
import com.viettel.jobfinder.modules.employee.dto.EmployeeResponseDto;
import com.viettel.jobfinder.modules.job.dto.JobResponseDto;

import lombok.Data;

@Data
public class ApplicationResponseDto {
  private long userEmployeeId;
  private long jobId;
  private boolean accepted;
  private String message;

  public ApplicationResponseDto(Application application) {
    this.userEmployeeId = application.getEmployee().getUser().getId();
    this.jobId = application.getJob().getId();
    this.accepted = application.isAccepted();
    this.message = application.getMessage();
  }
}
