package com.viettel.jobfinder.modules.application.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EditApplicationRequestDto {
  @NotBlank(message = "accepted field is required")
  private boolean accepted;

  private String message;
}
