package com.viettel.jobfinder.modules.skill.dto;

import com.viettel.jobfinder.modules.skill.Skill;

import lombok.Data;

@Data
public class SkillResponseDto {
  private long skillId;
  private long userId;
  private String title;
  private String certificateLink;
  private String score;

  public SkillResponseDto(Skill skill) {
    this.userId = skill.getEmployee().getUser().getId();
    this.skillId = skill.getId();
    this.title = skill.getTitle();
    this.certificateLink = skill.getCertificateLink();
    this.score = skill.getScore();
  }
}
