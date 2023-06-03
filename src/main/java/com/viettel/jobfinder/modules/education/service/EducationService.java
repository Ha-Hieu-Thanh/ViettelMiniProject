package com.viettel.jobfinder.modules.education.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.jobfinder.modules.education.Education;
import com.viettel.jobfinder.modules.education.dto.CreateEmployeeEducationRequestDto;
import com.viettel.jobfinder.modules.education.dto.EditEmployeeEducationRequestDto;
import com.viettel.jobfinder.modules.education.repositoty.EducationRepository;
import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.employee.repository.EmployeeRepository;
import com.viettel.jobfinder.modules.employee.service.EmployeeService;
import com.viettel.jobfinder.shared.exception.NotFoundException;

@Service
public class EducationService {
  @Autowired
  private EducationRepository educationRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private EmployeeService employeeService;

  public Education createEmployeeEducation(long userId,
      CreateEmployeeEducationRequestDto createEmployeeEducationRequestDto) {
    Education education = new Education();
    education.setSchoolName(createEmployeeEducationRequestDto.getSchoolName());
    education.setMajor(createEmployeeEducationRequestDto.getMajor());
    education.setStartDate(createEmployeeEducationRequestDto.getStartDate());
    education.setEndDate(createEmployeeEducationRequestDto.getEndDate());
    Employee employee = employeeService.getEmployeeInfo(userId);
    education.setEmployee(employee);
    return educationRepository.save(education);
  }

  public Education editEmployeeEducation(long userId, long educationId,
      EditEmployeeEducationRequestDto editEmployeeEducationRequestDto) {
    Education education = educationRepository.findById(educationId)
        .orElseThrow(() -> new NotFoundException("Education not found"));
    // Check if the user has the education entry
    Employee employee = employeeService.getEmployeeInfo(userId);
    if (!employee.getEducations().contains(education)) {
      throw new NotFoundException("Education not found || You don't have permission to edit this education");
    }
    if (Objects.nonNull(editEmployeeEducationRequestDto.getSchoolName())) {
      education.setSchoolName(editEmployeeEducationRequestDto.getSchoolName());
    }
    if (Objects.nonNull(editEmployeeEducationRequestDto.getMajor())) {
      education.setMajor(editEmployeeEducationRequestDto.getMajor());
    }
    if (Objects.nonNull(editEmployeeEducationRequestDto.getStartDate())) {
      education.setStartDate(editEmployeeEducationRequestDto.getStartDate());
    }
    if (Objects.nonNull(editEmployeeEducationRequestDto.getEndDate())) {
      education.setEndDate(editEmployeeEducationRequestDto.getEndDate());
    }
    return educationRepository.save(education);
  }

  public void deleteEmployeeEducation(long userId, long educationId) {
    Education education = educationRepository.findById(educationId)
        .orElseThrow(() -> new NotFoundException("Education not found"));
    // Check if the user has the education entry
    Employee employee = employeeService.getEmployeeInfo(userId);
    if (!employee.getEducations().contains(education)) {
      throw new NotFoundException("Education not found || You don't have permission to delete this education");
    }
    educationRepository.delete(education);
  }
}
