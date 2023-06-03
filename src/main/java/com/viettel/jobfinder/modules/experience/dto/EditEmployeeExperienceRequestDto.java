package com.viettel.jobfinder.modules.experience.dto;

import lombok.Data;

@Data
public class EditEmployeeExperienceRequestDto {
  private String companyName;

  private String position;

  private String startDate;

  private String endDate;
}
