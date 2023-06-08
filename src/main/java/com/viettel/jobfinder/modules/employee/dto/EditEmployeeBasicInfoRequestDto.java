package com.viettel.jobfinder.modules.employee.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.viettel.jobfinder.shared.validation.ValidGender;
import com.viettel.jobfinder.shared.validation.ValidWebsiteLink;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EditEmployeeBasicInfoRequestDto {
  // for user
  private String fullName;

  @Email(message = "email must be valid")
  private String email;

  @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "mobile phone must be valid")
  private String mobilePhone;

  private String location;
  // for employee
  @ValidGender
  private String gender;

  private String dateOfBirth;

  @ValidWebsiteLink
  private String linkedinLink;

  @ValidWebsiteLink
  private String githubLink;

  @Hidden
  public LocalDate getDateOfBirthAsLocalDate() {
    if (dateOfBirth == null || dateOfBirth.isEmpty()) {
      return null; // Return null if the date of birth is not provided
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return LocalDate.parse(dateOfBirth, formatter);
  }

}
