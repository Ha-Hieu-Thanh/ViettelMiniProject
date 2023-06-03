package com.viettel.jobfinder.modules.education.dto;

import lombok.Data;

@Data
public class EditEmployeeEducationRequestDto {

  private String schoolName;

  private String major;

  private String startDate;

  private String endDate;
}
