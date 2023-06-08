package com.viettel.jobfinder.modules.user.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.shared.ERoleName;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

  @NotBlank(message = "username cannot be blank")
  @NonNull
  private String username;

  @NotBlank(message = "fullname cannot be blank")
  @NonNull
  private String fullname;

  @NotBlank(message = "password cannot be blank")
  @NonNull
  private String password;

  @NotBlank(message = "email cannot be blank")
  @Email(message = "email must be valid")
  @NonNull
  private String email;
  @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "mobile phone must be valid")
  private String mobilePhone;
  private String location;

  @NotNull(message = "role cannot be null")
  @Enumerated(EnumType.STRING)
  private ERoleName role;

  public User toUser() {
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setEmail(email);
    user.setMobilePhone(mobilePhone);
    user.setLocation(location);
    user.setRole(role);
    user.setFullName(fullname);
    return user;
  }

}
