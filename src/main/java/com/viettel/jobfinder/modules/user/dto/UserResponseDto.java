package com.viettel.jobfinder.modules.user.dto;

import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.shared.ERoleName;

import lombok.Data;

@Data
public class UserResponseDto {

  private String username;
  private String fullName;
  private String email;
  private String mobilePhone;
  private String location;
  private ERoleName role;

  public static UserResponseDto fromUser(User user) {
    UserResponseDto dto = new UserResponseDto();
    dto.setUsername(user.getUsername());
    dto.setFullName(user.getFullName());
    dto.setEmail(user.getEmail());
    dto.setMobilePhone(user.getMobilePhone());
    dto.setLocation(user.getLocation());
    dto.setRole(user.getRole());
    return dto;
  }

}
