package com.viettel.jobfinder.modules.skill.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class CreateEmployeeSkillRequestDto {
  @NotBlank(message = "Title cant be blank ")
  @NonNull
  private String title;

  private String certificateLink;

  private String score;
}
