package com.viettel.jobfinder.modules.employee.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.employee.dto.EditEmployeeBasicInfoRequestDto;
import com.viettel.jobfinder.modules.employee.dto.EmployeeResponseDto;
import com.viettel.jobfinder.modules.employee.repository.EmployeeRepository;
import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.modules.user.repository.UserRepository;
import com.viettel.jobfinder.modules.user.service.UserService;
import com.viettel.jobfinder.shared.exception.NotFoundException;

@Service
public class EmployeeService {
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;

  public Employee editBasicInfo(long userId, EditEmployeeBasicInfoRequestDto data) {
    // find employee by userid if not found throw not found exception
    Employee employee = employeeRepository.findByUser_Id(userId)
        .orElseThrow(() -> new NotFoundException("Employee not found"));
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
    // update employee
    if (Objects.nonNull(data.getGender())) {
      employee.setGender(data.getGender());
    }
    if (Objects.nonNull(data.getDateOfBirth())) {
      employee.setDateOfBirth(data.getDateOfBirthAsLocalDate());
    }
    if (Objects.nonNull(data.getLinkedinLink())) {
      employee.setLinkedinLink(data.getLinkedinLink());
    }
    if (Objects.nonNull(data.getGithubLink())) {
      employee.setGithubLink(data.getGithubLink());
    }

    // save employee
    userRepository.save(user);
    employeeRepository.save(employee);
    return employee;
  }

  public Employee getEmployeeInfo(long userId) {
    Employee employee = employeeRepository.findByUser_Id(userId)
        .orElseThrow(() -> new NotFoundException("Employee not found"));
    return employee;
  }

  public List<Employee> getEmployees() {
    return employeeRepository.findAll();
  }

}
