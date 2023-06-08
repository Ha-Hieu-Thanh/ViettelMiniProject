package com.viettel.jobfinder.modules.skill.dto;

import com.viettel.jobfinder.shared.validation.ValidWebsiteLink;

import lombok.Data;

@Data
public class EditEmployeeSkillRequestDto {

  private String title;

  @ValidWebsiteLink
  private String certificateLink;

  private String score;
}
