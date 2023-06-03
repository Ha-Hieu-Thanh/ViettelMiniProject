package com.viettel.jobfinder.modules.employer.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.jobfinder.modules.employer.Employer;
import com.viettel.jobfinder.modules.employer.dto.EditEmployerBasicInfoRequestDto;
import com.viettel.jobfinder.modules.employer.repository.EmployerRepository;
import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.modules.user.repository.UserRepository;
import com.viettel.jobfinder.modules.user.service.UserService;
import com.viettel.jobfinder.shared.exception.NotFoundException;

@Service
public class EmployerService {
  @Autowired
  private EmployerRepository employerRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;

  public Employer editBasicInfo(long userId, EditEmployerBasicInfoRequestDto data) {
    Employer employer = employerRepository.findByUser_Id(userId)
        .orElseThrow(() -> new NotFoundException("Employer not found"));
    User user = userService.getUser(userId);
    // update user
    if (Objects.nonNull(data.getFullName())) {
      user.setFullName(data.getFullName());
    }
    if (Objects.nonNull(data.getEmail())) {
      user.setEmail(data.getEmail());
    }
    if (Objects.nonNull(data.getMobilePhone())) {
      user.setMobilePhone(data.getMobilePhone());
    }
    if (Objects.nonNull(data.getLocation())) {
      user.setLocation(data.getLocation());
    }

    // update employer
    if (Objects.nonNull(data.getWebsiteLink())) {
      employer.setWebsiteLink(data.getWebsiteLink());
    }
    if (Objects.nonNull(data.getDescription())) {
      employer.setDescription(data.getDescription());
    }
    // save employer
    userRepository.save(user);
    employerRepository.save(employer);
    return employer;
  }

  public Employer getEmployerInfo(long userId) {
    return employerRepository.findByUser_Id(userId)
        .orElseThrow(() -> new NotFoundException("Employer not found"));
  }

  public List<Employer> getEmployers() {
    return employerRepository.findAll();
  }
}
