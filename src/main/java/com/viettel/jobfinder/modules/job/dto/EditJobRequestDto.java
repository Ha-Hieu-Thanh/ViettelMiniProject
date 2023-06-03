package com.viettel.jobfinder.modules.job.dto;

import lombok.Data;

@Data
public class EditJobRequestDto {

  private String title;

  private String description;

  private String location;

  private String salary;

  private boolean active;
}
