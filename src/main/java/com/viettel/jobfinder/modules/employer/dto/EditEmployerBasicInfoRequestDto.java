package com.viettel.jobfinder.modules.employer.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.viettel.jobfinder.shared.validation.ValidWebsiteLink;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EditEmployerBasicInfoRequestDto {
  // for user
  private String fullName;

  @Email(message = "email must be valid")
  private String email;

  @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "mobile phone must be valid")
  private String mobilePhone;

  private String location;
  // for employer
  @ValidWebsiteLink
  private String websiteLink;

  private String description;
}
