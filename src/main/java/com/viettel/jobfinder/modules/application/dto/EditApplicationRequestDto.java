package com.viettel.jobfinder.modules.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EditApplicationRequestDto {
  @NotNull(message = "accepted field is required")
  private boolean accepted;

  private String message;
}
