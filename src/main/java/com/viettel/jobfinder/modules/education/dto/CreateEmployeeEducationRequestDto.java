package com.viettel.jobfinder.modules.education.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateEmployeeEducationRequestDto {
  @NonNull
  @NotBlank(message = "School name cant be blank ")
  private String schoolName;

  @NonNull
  @NotBlank(message = "Major cant be blank ")
  private String major;

  private String startDate;

  private String endDate;
}
