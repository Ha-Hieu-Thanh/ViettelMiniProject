package com.viettel.jobfinder.modules.job.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Data
@NoArgsConstructor
public class CreateJobRequestDto {
  @NonNull
  @NotBlank(message = "title is required")
  private String title;

  @NonNull
  @NotBlank(message = "description is required")
  private String description;

  @NonNull
  @NotBlank(message = "location is required")
  private String location;

  @NonNull
  @NotBlank(message = "salary is required")
  private String salary;

  private boolean active;
}
