package com.viettel.jobfinder.modules.experience.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.employee.repository.EmployeeRepository;
import com.viettel.jobfinder.modules.employee.service.EmployeeService;
import com.viettel.jobfinder.modules.experience.Experience;
import com.viettel.jobfinder.modules.experience.dto.CreateEmployeeExperienceRequestDto;
import com.viettel.jobfinder.modules.experience.dto.EditEmployeeExperienceRequestDto;
import com.viettel.jobfinder.modules.experience.repository.ExperienceRepository;
import com.viettel.jobfinder.shared.exception.NotFoundException;

@Service
public class ExperienceService {
  @Autowired
  private ExperienceRepository experienceRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private EmployeeService employeeService;

  public Experience createEmployeeExperience(long userId,
      CreateEmployeeExperienceRequestDto createEmployeeExperienceRequestDto) {
    Experience experience = new Experience();
    experience.setCompanyName(createEmployeeExperienceRequestDto.getCompanyName());
    experience.setPosition(createEmployeeExperienceRequestDto.getPosition());
    experience.setStartDate(createEmployeeExperienceRequestDto.getStartDate());
    experience.setEndDate(createEmployeeExperienceRequestDto.getEndDate());

    Employee employee = employeeService.getEmployeeInfo(userId);
    experience.setEmployee(employee);

    return experienceRepository.save(experience);
  }

  public Experience editEmployeeExperience(long userId, long experienceId,
      EditEmployeeExperienceRequestDto editEmployeeExperienceRequestDto) {
    Experience experience = experienceRepository.findById(experienceId)
        .orElseThrow(() -> new NotFoundException("Experience not found"));

    Employee employee = employeeService.getEmployeeInfo(userId);
    if (!employee.equals(experience.getEmployee())) {
      throw new NotFoundException("Experience not found || You don't have permission to edit this experience");
    }

    if (Objects.nonNull(editEmployeeExperienceRequestDto.getCompanyName())) {
      experience.setCompanyName(editEmployeeExperienceRequestDto.getCompanyName());
    }
    if (Objects.nonNull(editEmployeeExperienceRequestDto.getPosition())) {
      experience.setPosition(editEmployeeExperienceRequestDto.getPosition());
    }
    if (Objects.nonNull(editEmployeeExperienceRequestDto.getStartDate())) {
      experience.setStartDate(editEmployeeExperienceRequestDto.getStartDate());
    }
    if (Objects.nonNull(editEmployeeExperienceRequestDto.getEndDate())) {
      experience.setEndDate(editEmployeeExperienceRequestDto.getEndDate());
    }

    return experienceRepository.save(experience);
  }

  public void deleteEmployeeExperience(long userId, long experienceId) {
    Experience experience = experienceRepository.findById(experienceId)
        .orElseThrow(() -> new NotFoundException("Experience not found"));

    Employee employee = employeeService.getEmployeeInfo(userId);
    if (!employee.equals(experience.getEmployee())) {
      throw new NotFoundException("Experience not found || You don't have permission to delete this experience");
    }

    experienceRepository.delete(experience);
  }
}
