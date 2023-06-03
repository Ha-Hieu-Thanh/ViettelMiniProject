package com.viettel.jobfinder.modules.employer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EditEmployerBasicInfoRequestDto {
  // for user
  private String fullName;

  private String email;

  private String mobilePhone;

  private String location;
  // for employer
  private String websiteLink;

  private String description;
}
