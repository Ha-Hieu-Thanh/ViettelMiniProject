package com.viettel.jobfinder.modules.application.dto;

import lombok.Data;

@Data
public class EditApplicationRequestDto {
  private boolean accepted;

  private String message;
}
