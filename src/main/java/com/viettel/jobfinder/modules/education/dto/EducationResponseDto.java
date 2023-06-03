package com.viettel.jobfinder.modules.education.dto;

import com.viettel.jobfinder.modules.education.Education;

import lombok.Data;

@Data
public class EducationResponseDto {
  private long educationId;
  private long userId;
  private String schoolName;
  private String major;
  private String startDate;
  private String endDate;

  public EducationResponseDto(Education education) {
    this.educationId = education.getId();
    this.userId = education.getEmployee().getUser().getId();
    this.schoolName = education.getSchoolName();
    this.major = education.getMajor();
    this.startDate = education.getStartDate();
    this.endDate = education.getEndDate();
  }
}
