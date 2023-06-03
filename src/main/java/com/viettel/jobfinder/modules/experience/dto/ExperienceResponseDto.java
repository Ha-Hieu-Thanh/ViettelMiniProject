package com.viettel.jobfinder.modules.experience.dto;

import com.viettel.jobfinder.modules.experience.Experience;

import lombok.Data;

@Data
public class ExperienceResponseDto {
  private long experienceId;
  private long userId;
  private String companyName;
  private String position;
  private String startDate;
  private String endDate;

  public ExperienceResponseDto(Experience experience) {
    this.experienceId = experience.getId();
    this.userId = experience.getEmployee().getUser().getId();
    this.companyName = experience.getCompanyName();
    this.position = experience.getPosition();
    this.startDate = experience.getStartDate();
    this.endDate = experience.getEndDate();
  }
}
