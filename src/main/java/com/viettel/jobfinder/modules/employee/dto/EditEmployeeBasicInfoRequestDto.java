package com.viettel.jobfinder.modules.employee.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EditEmployeeBasicInfoRequestDto {
  // for user
  private String fullName;

  private String email;

  private String mobilePhone;

  private String location;
  // for employee
  private String gender;

  private String dateOfBirth;

  private String linkedinLink;

  private String githubLink;

  public LocalDate getDateOfBirthAsLocalDate() {
    if (dateOfBirth == null || dateOfBirth.isEmpty()) {
      return null; // Return null if the date of birth is not provided
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return LocalDate.parse(dateOfBirth, formatter);
  }

}
