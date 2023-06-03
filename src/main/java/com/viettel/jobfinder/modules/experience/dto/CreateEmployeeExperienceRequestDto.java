package com.viettel.jobfinder.modules.experience.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateEmployeeExperienceRequestDto {
  @NotBlank(message = "Company name cannot be blank")
  private String companyName;

  @NotBlank(message = "Position cannot be blank")
  private String position;

  private String startDate;

  private String endDate;
}
